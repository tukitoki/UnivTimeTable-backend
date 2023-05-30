package ru.vsu.cs.timetable.dto.week_time;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.entity.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.entity.enums.WeekType;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
public class DayTimes {

    private DayOfWeekEnum dayOfWeek;
    private Map<WeekType, List<LocalTime>> weekTimes;
}
