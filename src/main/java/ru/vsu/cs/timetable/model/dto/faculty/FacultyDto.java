package ru.vsu.cs.timetable.model.dto.faculty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class FacultyDto {

    private Long id;
    @NotNull
    @NotBlank
    private String name;
}
