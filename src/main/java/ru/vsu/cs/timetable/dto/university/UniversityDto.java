package ru.vsu.cs.timetable.dto.university;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @NotBlank
    private String univName;
    @NotNull
    @NotBlank
    private String city;
    private List<FacultyDto> faculties;
}
