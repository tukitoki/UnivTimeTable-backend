package ru.vsu.cs.timetable.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.Time;

@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
public class ClassDto {

    private String subjectName;
    private Time startTime;
    private Integer audience;
    private String dayOfWeek;
    private String typeOfClass;
}
