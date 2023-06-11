package ru.vsu.cs.timetable.model.dto.university;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.faculty.FacultyResponse;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
@Schema(description = "Полная информация о университете с группами")
public class UniversityResponse {

    @Schema(description = "Id университета", example = "1")
    private Long id;
    @NotNull
    @NotBlank
    @Schema(description = "Название университета", example = "Воронежский государственный университет")
    private String universityName;
    @NotNull
    @NotBlank
    @Schema(description = "город университета", example = "Воронеж")
    private String city;
    @Schema(description = "Информация о факультетах")
    private List<FacultyResponse> facultyDtos;
}
