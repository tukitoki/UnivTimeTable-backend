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
@Schema(description = "Информация об университете")
public class UniversityDto {

    @Schema(description = "Id университета", example = "1")
    private Long id;
    @NotNull
    @NotBlank
    @Schema(description = "Название университета", example = "Воронежский государственный университет")
    private String universityName;
    @NotNull
    @NotBlank
    @Schema(description = "Город университета", example = "Воронеж")
    private String city;
}
