package ru.vsu.cs.timetable.dto.university;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
public class UniversityDto {

    private Long id;
    private String university;
    private String city;
}
