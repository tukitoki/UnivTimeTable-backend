package ru.vsu.cs.timetable.dto.university;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @NotBlank
    private String universityName;
    @NotNull
    @NotBlank
    private String city;
}
