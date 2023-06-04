package ru.vsu.cs.timetable.model.dto.univ_class;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.entity.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.model.entity.enums.TypeClass;
import ru.vsu.cs.timetable.model.entity.enums.WeekType;

import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class ClassDto {

    private String subjectName;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer audience;
    private DayOfWeekEnum dayOfWeek;
    private TypeClass typeOfClass;
    private WeekType weekType;
    private Integer courseNumber;
    private List<Integer> groupsNumber;
}
