package ru.vsu.cs.timetable.model.dto.faculty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateFacultyRequest {

    @NotNull
    @NotBlank
    private String name;
}
