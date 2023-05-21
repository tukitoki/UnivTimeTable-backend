package ru.vsu.cs.timetable.dto.univ_class;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class ClassDto {

    private String subjectName;
    private String startTime;
    private Integer audience;
    private String dayOfWeek;
    private String typeOfClass;
    private String weekType;
}
