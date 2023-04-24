package ru.vsu.cs.timetable.dto.faculty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class FacultyDto {

    private Long id;
    private String name;
}
