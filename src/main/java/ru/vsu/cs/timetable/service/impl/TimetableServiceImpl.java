package ru.vsu.cs.timetable.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.timetable.dto.TimetableResponse;
import ru.vsu.cs.timetable.entity.Request;
import ru.vsu.cs.timetable.entity.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.entity.enums.TypeClass;
import ru.vsu.cs.timetable.entity.enums.WeekType;
import ru.vsu.cs.timetable.mapper.ClassMapper;
import ru.vsu.cs.timetable.planner.TimetableSolver;
import ru.vsu.cs.timetable.planner.model.Audience;
import ru.vsu.cs.timetable.planner.model.Class;
import ru.vsu.cs.timetable.planner.model.Timeslot;
import ru.vsu.cs.timetable.planner.model.Timetable;
import ru.vsu.cs.timetable.repository.AudienceRepository;
import ru.vsu.cs.timetable.repository.ClassRepository;
import ru.vsu.cs.timetable.repository.RequestRepository;
import ru.vsu.cs.timetable.repository.TimetableRepository;
import ru.vsu.cs.timetable.service.TimetableService;
import ru.vsu.cs.timetable.service.UserService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static ru.vsu.cs.timetable.utils.ClassTimeUtils.*;

@RequiredArgsConstructor
@Service
public class TimetableServiceImpl implements TimetableService {

    private final UserService userService;
    private final TimetableRepository timetableRepository;
    private final RequestRepository requestRepository;
    private final AudienceRepository audienceRepository;
    private final ClassRepository classRepository;
    private final ClassMapper classMapper;
    private final TimetableSolver timetableSolver;

    @Override
    public TimetableResponse getTimetable(String username) {
        return null;
    }

    @Override
    public void downloadTimetable(String username) {

    }

    @Override
    public void makeTimetable(String username) {
        var user = userService.getUserByUsername(username);

        List<LocalTime> possibleTimes = getPossibleClassTimes();
        List<Timeslot> timeslots = new LinkedList<>();
        for (var day : DayOfWeekEnum.values()) {
            possibleTimes.forEach(time -> {
                for (var weekType : WeekType.values()) {
                    timeslots.add(Timeslot.builder()
                            .dayOfWeekEnum(day)
                            .weekType(weekType)
                            .startTime(time)
                            .endTime(calculateLastTimeByStart(time))
                            .build());
                }
            });
        }

        List<Class> classes = new ArrayList<>(getClasses(requestRepository
                .findAllByGroupFacultyOrderByTypeClass(user.getFaculty())));

        List<Audience> audiences = audienceRepository.findAllByFaculty(user.getFaculty())
                .stream()
                .map(audience -> {
                    Audience planningAudience = Audience.builder()
                            .audienceNumber(audience.getAudienceNumber())
                            .capacity(audience.getCapacity())
                            .equipments(audience.getEquipments())
                            .build();
                    return planningAudience;
                })
                .toList();

        Timetable problemTimetable = Timetable.builder()
                .timeslots(timeslots)
                .classes(classes)
                .audiences(audiences)
                .build();
        Timetable solutionTimetable = timetableSolver.solve(problemTimetable);
    }

    private List<Class> getClasses(List<Request> requests) {
        List<Class> lectureClasses = new ArrayList<>();
        List<Class> seminarClasses = new ArrayList<>();
        long id = 1L;
        for (var request : requests) {
            double subjectsInWeek = request.getSubjectHourPerWeek().doubleValue()
                    * 60 / ACADEMICAL_PAIR_MINUTES;
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
            List<Class> localClasses = new LinkedList<>();
            for (int countSubjectInWeek = 0; countSubjectInWeek < subjectsInWeek; countSubjectInWeek++) {
                localClasses.add(classMapper.toPlanningEntity(id++, request));
            }
            if (request.getTypeClass().equals(TypeClass.LECTURE)) {
                lectureClasses.addAll(localClasses);
            } else {
                seminarClasses.addAll(localClasses);
            }
        }
        List<Class> classes = new ArrayList<>();
        classes.addAll(lectureClasses);
        classes.addAll(seminarClasses);
        return classes;
    }
}
