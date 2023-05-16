package ru.vsu.cs.timetable.dto.university;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CreateUnivRequest {

    @NotNull
    @NotBlank
    private String universityName;
    @NotNull
    @NotBlank
    private String city;
}
