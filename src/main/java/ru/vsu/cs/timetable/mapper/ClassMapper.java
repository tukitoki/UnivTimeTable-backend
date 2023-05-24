package ru.vsu.cs.timetable.mapper;

import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.entity.Request;
import ru.vsu.cs.timetable.planner.model.Class;
import ru.vsu.cs.timetable.planner.model.Timeslot;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.vsu.cs.timetable.utils.ClassTimeUtils.calculateLastTimeByStart;

@Component
public class ClassMapper {

    public Class toPlanningEntity(Long id, Request request) {
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
        return Class.builder()
                .id(id)
                .subjectName(request.getSubjectName())
                .typeClass(request.getTypeClass())
                .lecturer(request.getLecturer())
                .impossibleTimes(impossibleTimes)
                .groups(new HashSet<>(Set.of(request.getGroup())))
                .requiredEquipments(request.getRequiredEquipments())
                .build();
    }
}
