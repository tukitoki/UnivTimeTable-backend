package ru.vsu.cs.timetable.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.timetable.dto.TimetableResponse;
import ru.vsu.cs.timetable.entity.Class;
import ru.vsu.cs.timetable.entity.Request;
import ru.vsu.cs.timetable.entity.User;
import ru.vsu.cs.timetable.entity.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.entity.enums.TypeClass;
import ru.vsu.cs.timetable.entity.enums.UserRole;
import ru.vsu.cs.timetable.entity.enums.WeekType;
import ru.vsu.cs.timetable.exception.AudienceException;
import ru.vsu.cs.timetable.exception.TimetableException;
import ru.vsu.cs.timetable.mapper.AudienceMapper;
import ru.vsu.cs.timetable.mapper.ClassMapper;
import ru.vsu.cs.timetable.mapper.TimetableMapper;
import ru.vsu.cs.timetable.planner.TimetableSolver;
import ru.vsu.cs.timetable.planner.model.PlanningClass;
import ru.vsu.cs.timetable.planner.model.PlanningTimetable;
import ru.vsu.cs.timetable.planner.model.Timeslot;
import ru.vsu.cs.timetable.repository.AudienceRepository;
import ru.vsu.cs.timetable.repository.ClassRepository;
import ru.vsu.cs.timetable.repository.RequestRepository;
import ru.vsu.cs.timetable.repository.TimetableRepository;
import ru.vsu.cs.timetable.service.ExcelService;
import ru.vsu.cs.timetable.service.TimetableService;
import ru.vsu.cs.timetable.service.UserService;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static ru.vsu.cs.timetable.utils.TimeUtils.*;

@RequiredArgsConstructor
@Service
public class TimetableServiceImpl implements TimetableService {

    private final UserService userService;
    private final ExcelService excelService;
    private final TimetableRepository timetableRepository;
    private final ClassRepository classRepository;
    private final RequestRepository requestRepository;
    private final AudienceRepository audienceRepository;
    private final ClassMapper classMapper;
    private final AudienceMapper audienceMapper;
    private final TimetableMapper timetableMapper;
    private final TimetableSolver timetableSolver;

    @Override
    @Transactional(readOnly = true)
    public TimetableResponse getTimetable(String username) {
        var user = userService.getUserByUsername(username);
        if (user.getRole() == UserRole.ADMIN) {
            throw TimetableException.CODE.ADMIN_CANT_ACCESS.get();
        }

        var classes = getTimetableByUserAndCurrWeek(user);

        return TimetableResponse.builder()
                .classes(classes)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Workbook downloadTimetable(String username) {
        var user = userService.getUserByUsername(username);
        if (user.getRole() == UserRole.ADMIN) {
            throw TimetableException.CODE.ADMIN_CANT_ACCESS.get();
        }

        return excelService.getExcelTimetable(getTimetableByUser(user));
    }

    @Override
    @Async
    @Transactional
    public CompletableFuture<Void> makeTimetable(String username) {
        var lecturer = userService.getUserByUsername(username);

        List<Timeslot> timeslots = getAllPossibleTimeslots();

        var classes = new ArrayList<>(getClasses(requestRepository
                .findAllByGroupFacultyOrderByTypeClass(lecturer.getFaculty())));
        var audiences = audienceRepository.findAllByFaculty(lecturer.getFaculty())
                .stream()
                .map(audienceMapper::toPlanningAudience)
                .toList();

        PlanningTimetable problemTimetable = PlanningTimetable.builder()
                .timeslots(timeslots)
                .classes(classes)
                .audiences(audiences)
                .build();

        PlanningTimetable solutionTimetable = timetableSolver.solve(problemTimetable);
        if (solutionTimetable.getHardSoftScore().hardScore() > 0) {
            throw TimetableException.CODE.TIMETABLE_CANT_BE_GENERATED.get();
        }

        var usedAudiences = solutionTimetable.getAudiences().stream()
                .map(planningAudience -> audienceRepository.findById(planningAudience.getId())
                        .orElseThrow(AudienceException.CODE.ID_NOT_FOUND::get))
                .toList();

        var planningClasses = solutionTimetable.getClasses();
        for (var dayOfWeek : DayOfWeekEnum.values()) {
            var dayOfWeekClasses = planningClasses.stream()
                    .filter(planningClass -> planningClass.getTimeslot().getDayOfWeekEnum() == dayOfWeek)
                    .toList();

            var timetable = timetableMapper.toEntity(dayOfWeek, dayOfWeekClasses, usedAudiences);
            timetableRepository.save(timetable);
        }

        return new CompletableFuture<>();
    }

    private List<PlanningClass> getClasses(List<Request> requests) {
        List<PlanningClass> lectureClasses = new ArrayList<>();
        List<PlanningClass> seminarClasses = new ArrayList<>();
        long id = 1L;
        for (var request : requests) {
            double subjectsInWeek = request.getSubjectHourPerWeek().doubleValue()
                    * MINUTES_IN_HOUR / ACADEMICAL_PAIR_MINUTES;

            boolean alreadyPresent = false;
            for (var lectureClass : lectureClasses) {
                if (lectureClass.getLecturer().equals(request.getLecturer())
                        && !lectureClass.getGroups().contains(request.getGroup())) {
                    lectureClass.getGroups().add(request.getGroup());
                    alreadyPresent = true;
                }
            }
            if (alreadyPresent) {
                continue;
            }

            List<PlanningClass> localClasses = new LinkedList<>();
            for (int countSubjectInWeek = 0; countSubjectInWeek < subjectsInWeek; countSubjectInWeek++) {
                localClasses.add(classMapper.toPlanningEntity(id++, request));
            }

            if (request.getTypeClass().equals(TypeClass.LECTURE)) {
                lectureClasses.addAll(localClasses);
            } else {
                seminarClasses.addAll(localClasses);
            }

        }
        List<PlanningClass> classes = new ArrayList<>();
        classes.addAll(lectureClasses);
        classes.addAll(seminarClasses);

        return classes;
    }

    private Map<String, List<Class>> getTimetableByUserAndCurrWeek(User user) {
        Map<String, List<Class>> timetable = new HashMap<>();
        WeekType currWeek = getCurrentWeekType();
        int totalClassesSize = 0;

        for (var dayOfWeek : DayOfWeekEnum.values()) {
            List<Class> classes;

            if (user.getRole() == UserRole.HEADMAN) {
                classes = classRepository.findAllByWeekTypeAndGroupsContainsAndDayOfWeekOrderByStartTimeAsc(currWeek,
                        user.getGroup(), dayOfWeek);
            } else if (user.getRole() == UserRole.LECTURER) {
                classes = classRepository.findAllByWeekTypeAndLecturerAndDayOfWeekOrderByStartTimeAsc(currWeek,
                        user, dayOfWeek);
            } else {
                throw TimetableException.CODE.ADMIN_CANT_ACCESS.get();
            }

            totalClassesSize += classes.size();
            timetable.put(dayOfWeek.toString(), classes);
        }

        if (totalClassesSize == 0) {
            throw TimetableException.CODE.TIMETABLE_WAS_NOT_MADE.get();
        }

        return timetable;
    }

    private Map<DayOfWeekEnum, List<Class>> getTimetableByUser(User user) {
        Map<DayOfWeekEnum, List<Class>> timetable = new HashMap<>();
        int totalClassesSize = 0;

        for (var dayOfWeek : DayOfWeekEnum.values()) {
            List<Class> classes;

            if (user.getRole() == UserRole.HEADMAN) {
                classes = classRepository.findAllByGroupsContainsAndDayOfWeek(user.getGroup(), dayOfWeek);
            } else if (user.getRole() == UserRole.LECTURER) {
                classes = classRepository.findAllByLecturerAndDayOfWeek(user, dayOfWeek);
            } else {
                throw TimetableException.CODE.ADMIN_CANT_ACCESS.get();
            }

            totalClassesSize += classes.size();
            timetable.put(dayOfWeek, classes);
        }

        if (totalClassesSize == 0) {
            throw TimetableException.CODE.TIMETABLE_WAS_NOT_MADE.get();
        }

        return timetable;
    }
}
