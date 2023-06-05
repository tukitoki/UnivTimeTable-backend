package ru.vsu.cs.timetable.model.dto.faculty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Информация для создания факультета")
public class CreateFacultyRequest {

    @NotNull
    @NotBlank
    private String name;
}
