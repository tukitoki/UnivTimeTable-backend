package ru.vsu.cs.timetable.model.dto.faculty;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Информация для создания факультета")
public class FacultyDto {

    private Long id;
    @NotNull
    @NotBlank
    private String name;
}
