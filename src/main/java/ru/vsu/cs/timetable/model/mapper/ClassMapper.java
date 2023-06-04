package ru.vsu.cs.timetable.model.mapper;

import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.logic.planner.model.PlanningClass;
import ru.vsu.cs.timetable.logic.planner.model.Timeslot;
import ru.vsu.cs.timetable.model.dto.univ_class.ClassDto;
import ru.vsu.cs.timetable.model.entity.Audience;
import ru.vsu.cs.timetable.model.entity.Class;
import ru.vsu.cs.timetable.model.entity.Group;
import ru.vsu.cs.timetable.model.entity.Request;
import ru.vsu.cs.timetable.utils.TimeUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.vsu.cs.timetable.utils.TimeUtils.calculateEndTimeByStart;

@Component
public class ClassMapper {

    public PlanningClass toPlanningEntity(Long id, Request request) {
        List<Timeslot> impossibleTimes = request.getImpossibleTimes()
                .stream()
                .map(impossibleTime -> {
                    var timeslot = Timeslot.builder()
                            .dayOfWeekEnum(impossibleTime.getDayOfWeek())
                            .startTime(impossibleTime.getTimeFrom())
                            .endTime(calculateEndTimeByStart(impossibleTime.getTimeFrom()))
                            .build();
                    return timeslot;
                })
                .toList();
        return PlanningClass.builder()
                .id(id)
                .subjectName(request.getSubjectName())
                .typeClass(request.getTypeClass())
                .lecturer(request.getLecturer())
                .impossibleTimes(impossibleTimes)
                .groups(new HashSet<>(Set.of(request.getGroup())))
                .requiredEquipments(request.getRequiredEquipments())
                .build();
    }

    public Class toEntity(PlanningClass planningClass, Audience audience) {
        var timeSlot = planningClass.getTimeslot();

        return Class.builder()
                .subjectName(planningClass.getSubjectName())
                .startTime(timeSlot.getStartTime())
                .typeClass(planningClass.getTypeClass())
                .dayOfWeek(timeSlot.getDayOfWeekEnum())
                .weekType(timeSlot.getWeekType())
                .lecturer(planningClass.getLecturer())
                .audience(audience)
                .groups(planningClass.getGroups())
                .build();
    }

    public Class toEntity(ClassDto classDto) {
        return Class.builder()
                .subjectName(classDto.getSubjectName())
                .startTime(classDto.getStartTime())
                .dayOfWeek(classDto.getDayOfWeek())
                .typeClass(classDto.getTypeOfClass())
                .weekType(classDto.getWeekType())
                .build();
    }

    public ClassDto toDto(Class aClass) {
        var courseNumber = aClass.getGroups().stream()
                .map(Group::getCourseNumber)
                .findFirst()
                .orElse(null);

        return ClassDto.builder()
                .subjectName(aClass.getSubjectName())
                .startTime(aClass.getStartTime())
                .endTime(TimeUtils.calculateEndTimeByStart(aClass.getStartTime()))
                .audience(aClass.getAudience().getAudienceNumber())
                .dayOfWeek(aClass.getDayOfWeek())
                .typeOfClass(aClass.getTypeClass())
                .weekType(aClass.getWeekType())
                .courseNumber(courseNumber)
                .groupsNumber(aClass.getGroups().stream()
                        .map(Group::getGroupNumber)
                        .toList())
                .build();
    }
}
