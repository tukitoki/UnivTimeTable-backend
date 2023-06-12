package ru.vsu.cs.timetable.model.dto.week_time;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.model.enums.WeekType;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
@Schema(description = "День недели с временем для числителя и знаменателя")
public class DayTimes {

    @Schema(description = "День недели", example = "Понедельник")
    private DayOfWeekEnum dayOfWeek;
    @Schema(description = "Типа недели со свободным временем", example = """
            {
                "Числитель": ["08:00:00", "09:45:00"],
                "Знаменатель": ["15:00:00", "16:45:00"]
            }
            """)
    private Map<WeekType, List<LocalTime>> weekTimes;
}
