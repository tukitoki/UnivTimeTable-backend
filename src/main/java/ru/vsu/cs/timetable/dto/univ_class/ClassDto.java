package ru.vsu.cs.timetable.dto.univ_class;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.entity.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.entity.enums.TypeClass;
import ru.vsu.cs.timetable.entity.enums.WeekType;

import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class ClassDto {

    private String subjectName;
    private LocalTime startTime;
    private Integer audience;
    private DayOfWeekEnum dayOfWeek;
    private TypeClass typeOfClass;
    private WeekType weekType;
}
