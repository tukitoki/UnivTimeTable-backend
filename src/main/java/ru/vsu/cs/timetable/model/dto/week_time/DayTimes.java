package ru.vsu.cs.timetable.model.dto.week_time;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.entity.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.model.entity.enums.WeekType;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
@Schema(description = "День недели с временем для числителя и знаменателя")
public class DayTimes {

    private DayOfWeekEnum dayOfWeek;
    private Map<WeekType, List<LocalTime>> weekTimes;
}
