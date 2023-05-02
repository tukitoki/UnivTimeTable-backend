package ru.vsu.cs.timetable.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.vsu.cs.timetable.dto.user.JwtDto;
import ru.vsu.cs.timetable.dto.user.UserLoginDto;

@Tag(name = "Auth API", description = "API для входа в систему и refresh JWT-token")
@SecurityRequirement(name = "JWT auth")
public interface AuthApi {

    @Operation(summary = "Возвращает jwt и refresh token's")
    JwtDto loginUser(
            @Parameter(description = "Логин и пароль пользователя")
            UserLoginDto userLoginDto
    );

    @Operation(summary = "Возвращает обновленные jwt и refresh token's")
    JwtDto refreshToken(
            @Parameter(description = "Refresh токен")
            String refreshToken
    );
}
