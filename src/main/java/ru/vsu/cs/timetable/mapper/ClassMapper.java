package ru.vsu.cs.timetable.mapper;

import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.entity.Audience;
import ru.vsu.cs.timetable.entity.Class;
import ru.vsu.cs.timetable.entity.Request;
import ru.vsu.cs.timetable.planner.model.PlanningClass;
import ru.vsu.cs.timetable.planner.model.Timeslot;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.vsu.cs.timetable.utils.TimeUtils.calculateLastTimeByStart;

@Component
public class ClassMapper {

    public PlanningClass toPlanningEntity(Long id, Request request) {
        List<Timeslot> impossibleTimes = request.getImpossibleTimes()
                .stream()
                .map(impossibleTime -> {
                    var timeslot = Timeslot.builder()
                            .dayOfWeekEnum(impossibleTime.getDayOfWeek())
                            .startTime(impossibleTime.getTimeFrom())
                            .endTime(calculateLastTimeByStart(impossibleTime.getTimeFrom()))
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
}
