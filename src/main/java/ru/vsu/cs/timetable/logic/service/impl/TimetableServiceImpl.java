package ru.vsu.cs.timetable.logic.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.timetable.exception.AudienceException;
import ru.vsu.cs.timetable.exception.TimetableException;
import ru.vsu.cs.timetable.exception.UserException;
import ru.vsu.cs.timetable.logic.planner.TimetableSolver;
import ru.vsu.cs.timetable.logic.planner.model.PlanningClass;
import ru.vsu.cs.timetable.logic.planner.model.PlanningTimetable;
import ru.vsu.cs.timetable.logic.planner.model.Timeslot;
import ru.vsu.cs.timetable.logic.service.ExcelService;
import ru.vsu.cs.timetable.logic.service.MailService;
import ru.vsu.cs.timetable.logic.service.TimetableService;
import ru.vsu.cs.timetable.logic.service.UserService;
import ru.vsu.cs.timetable.model.dto.TimetableResponse;
import ru.vsu.cs.timetable.model.dto.univ_class.ClassDto;
import ru.vsu.cs.timetable.model.entity.Class;
import ru.vsu.cs.timetable.model.entity.Group;
import ru.vsu.cs.timetable.model.entity.Request;
import ru.vsu.cs.timetable.model.entity.User;
import ru.vsu.cs.timetable.model.entity.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.model.entity.enums.TypeClass;
import ru.vsu.cs.timetable.model.entity.enums.WeekType;
import ru.vsu.cs.timetable.model.mapper.AudienceMapper;
import ru.vsu.cs.timetable.model.mapper.ClassMapper;
import ru.vsu.cs.timetable.model.mapper.TimetableMapper;
import ru.vsu.cs.timetable.repository.*;

import java.util.*;
import java.util.stream.Collectors;

import static ru.vsu.cs.timetable.model.entity.enums.UserRole.*;
import static ru.vsu.cs.timetable.utils.TimeUtils.*;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class TimetableServiceImpl implements TimetableService {

    private final UserService userService;
    private final ExcelService excelService;
    private final MailService mailService;
    private final TimetableRepository timetableRepository;
    private final ClassRepository classRepository;
    private final RequestRepository requestRepository;
    private final AudienceRepository audienceRepository;
    private final GroupRepository groupRepository;
    private final ClassMapper classMapper;
    private final AudienceMapper audienceMapper;
    private final TimetableMapper timetableMapper;
    private final TimetableSolver timetableSolver;

    @Override
    @Transactional(readOnly = true)
    public TimetableResponse getTimetable(String username) {
        var user = userService.getUserByUsername(username);
        if (user.getRole() == ADMIN) {
            throw TimetableException.CODE.ADMIN_CANT_ACCESS.get();
        }
        if (user.getRole() == HEADMAN && user.getGroup() == null) {
            throw UserException.CODE.HEADMAN_SHOULD_HAVE_GROUP.get();
        }

        var timetable = TimetableResponse.builder()
                .classes(new HashMap<>())
                .build();

        WeekType currWeek = getCurrentWeekType();
        var classes = getTimetableByUserAndCurrWeek(user, currWeek);
        timetable.getClasses().put(currWeek, classes);

        WeekType nextWeek = currWeek == WeekType.DENOMINATOR
                ? WeekType.NUMERATOR
                : WeekType.DENOMINATOR;
        classes = getTimetableByUserAndCurrWeek(user, nextWeek);
        timetable.getClasses().put(nextWeek, classes);
        if (timetable.countTotalClassesSize() == 0) {
            throw TimetableException.CODE.TIMETABLE_WAS_NOT_MADE.get();
        }

        log.info("user: {}, was successfully called get timetable: {}", user, timetable);

        return timetable;
    }

    @Override
    @Transactional(readOnly = true)
    public Workbook downloadTimetable(String username) {
        var user = userService.getUserByUsername(username);
        if (user.getRole() == ADMIN) {
            throw TimetableException.CODE.ADMIN_CANT_ACCESS.get();
        }

        return excelService.getExcelTimetable(getTimetableByUser(user));
    }

    @Override
    @Async
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void makeTimetable(String username) {
        var lecturer = userService.getUserByUsername(username);
        if (classRepository.findAllByLecturer(lecturer).size() > 0) {
            throw TimetableException.CODE.TIMETABLE_WAS_ALREADY_MADE.get();
        }

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
        if (solutionTimetable.getHardSoftScore().hardScore() < 0) {
            String summaryViolations = solutionTimetable.getClasses().stream()
                    .map(PlanningClass::getHardViolation)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(System.lineSeparator()));

            mailService.sendTimetableCantMade(lecturer.getEmail(), summaryViolations);

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

        notificationUsersAboutTimetable(planningClasses);

        log.info("user: {}, was successfully called make timetable", lecturer);
    }

    @Override
    public void resetTimetable(String username) {
        var lecturer = userService.getUserByUsername(username);

        List<Group> groups = new ArrayList<>();
        groupRepository.findAll().forEach(groups::add);

        var allFacultyClasses = groups.stream()
                .filter(group -> group.getFaculty().equals(lecturer.getFaculty()))
                .flatMap(group -> group.getClasses().stream())
                .distinct()
                .toList();

        classRepository.deleteAll(allFacultyClasses);
        timetableRepository.deleteAll(timetableRepository.findAllByClassesEmpty());

        log.info("user: {}, was successfully called reset timetable for faculty {}", lecturer, lecturer.getFaculty());
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
                        && !lectureClass.getGroups().contains(request.getGroup())
                        && lectureClass.getGroups().stream()
                        .anyMatch(group -> group.getCourseNumber().equals(request.getGroup().getCourseNumber()))) {
                    lectureClass.getGroups().add(request.getGroup());
                    var planningClass = classMapper.toPlanningEntity(0L, request);
                    planningClass.getImpossibleTimes().forEach(impossibleTime -> {
                        if (!lectureClass.getImpossibleTimes().contains(impossibleTime)) {
                            lectureClass.getImpossibleTimes().add(impossibleTime);
                        }
                    });
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

    private Map<DayOfWeekEnum, List<ClassDto>> getTimetableByUserAndCurrWeek(User user, WeekType currWeek) {
        Map<DayOfWeekEnum, List<ClassDto>> timetable = new HashMap<>();

        for (var dayOfWeek : DayOfWeekEnum.values()) {
            List<Class> classes;

            if (user.getRole() == HEADMAN) {
                classes = classRepository.findAllByWeekTypeAndGroupsContainsAndDayOfWeekOrderByStartTimeAsc(currWeek,
                        user.getGroup(), dayOfWeek);
            } else if (user.getRole() == LECTURER) {
                classes = classRepository.findAllByWeekTypeAndLecturerAndDayOfWeekOrderByStartTimeAsc(currWeek,
                        user, dayOfWeek);
            } else {
                throw TimetableException.CODE.ADMIN_CANT_ACCESS.get();
            }

            var classDtos = classes.stream()
                    .map(classMapper::toDto)
                    .toList();
            timetable.put(dayOfWeek, classDtos);
        }

        return timetable;
    }

    private Map<DayOfWeekEnum, List<Class>> getTimetableByUser(User user) {
        Map<DayOfWeekEnum, List<Class>> timetable = new HashMap<>();
        int totalClassesSize = 0;

        for (var dayOfWeek : DayOfWeekEnum.values()) {
            List<Class> classes;

            if (user.getRole() == HEADMAN) {
                classes = classRepository.findAllByGroupsContainsAndDayOfWeek(user.getGroup(), dayOfWeek);
            } else if (user.getRole() == LECTURER) {
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

    private void notificationUsersAboutTimetable(List<PlanningClass> classes) {
        var lecturers = classes.stream()
                .map(PlanningClass::getLecturer)
                .distinct()
                .toList();

        var headmen = classes.stream()
                .flatMap(aClass -> aClass.getGroups().stream())
                .flatMap(group -> group.getUsers().stream())
                .distinct()
                .toList();

        lecturers.forEach(lecturer -> mailService.sendTimetableWasMade(lecturer.getEmail()));
        headmen.forEach(headman -> mailService.sendTimetableWasMade(headman.getEmail()));
    }
}
