package ru.vsu.cs.timetable.logic.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.vsu.cs.timetable.model.dto.user.JwtDto;
import ru.vsu.cs.timetable.model.dto.user.UserLoginDto;

@Validated
public interface AuthService {

    JwtDto loginUser(@NotNull @Valid UserLoginDto userLoginDto);

    JwtDto refreshToken(@NotNull @NotBlank String refreshToken);
}
