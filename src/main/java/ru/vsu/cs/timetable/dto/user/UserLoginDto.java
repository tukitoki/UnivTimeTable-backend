package ru.vsu.cs.timetable.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginDto {

    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;
}
