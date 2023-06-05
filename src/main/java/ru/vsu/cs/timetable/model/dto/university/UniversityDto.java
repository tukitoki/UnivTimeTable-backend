package ru.vsu.cs.timetable.model.dto.university;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Информация о университете")
public class UniversityDto {

    private Long id;
    @NotNull
    @NotBlank
    private String universityName;
    @NotNull
    @NotBlank
    private String city;
}
