package ru.vsu.cs.timetable.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
public class ClassDto {

    private String subjectName;
    private String startTime;
    private Integer audience;
    private String dayOfWeek;
    private String typeOfClass;
    private String weekType;
}
