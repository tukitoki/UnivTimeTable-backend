package ru.vsu.cs.timetable.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.model.entity.Audience;
import ru.vsu.cs.timetable.model.entity.Timetable;
import ru.vsu.cs.timetable.model.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.logic.planner.model.PlanningClass;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class TimetableMapper {

    private final ClassMapper classMapper;

    public Timetable toEntity(DayOfWeekEnum dayOfWeekEnum,
                              List<PlanningClass> planningClasses, List<Audience> audiences) {
        Timetable timetable = Timetable.builder()
                .dayOfWeek(dayOfWeekEnum)
                .classes(new LinkedList<>())
                .build();

        for (var planningClass : planningClasses) {
            var classAudience = audiences.stream()
                    .filter(audience -> audience.getId().equals(planningClass.getAudience().getId()))
                    .findFirst().orElse(new Audience());
            var univClass = classMapper.toEntity(planningClass, classAudience);
            univClass.setTimetable(timetable);
            timetable.getClasses().add(univClass);
        }

        return timetable;
    }
}
