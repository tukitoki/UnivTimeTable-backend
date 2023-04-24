package ru.vsu.cs.timetable.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Auth API", description = "API для входа в систему и refresh JWT-token")
@SecurityRequirement(name = "JWT auth")
public interface AuthApi {

    @Operation(summary = "Возвращает jwt и refresh token's")
    void loginUser();

    @Operation(summary = "Возвращает обновленные jwt и refresh token's")
    void refreshToken();
}
