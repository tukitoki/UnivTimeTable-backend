package ru.vsu.cs.timetable.dto.university;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.dto.faculty.FacultyDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
public class UniversityDto {

    private Long id;
    private String univName;
    private String city;
    private List<FacultyDto> faculites;
}
