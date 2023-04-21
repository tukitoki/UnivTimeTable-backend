package ru.vsu.cs.timetable.controller.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Auth API", description = "API для входа в систему и refresh JWT-token")
@SecurityRequirement(name = "JWT auth")
public interface AuthApi {

    void loginUser();

    void refreshToken();
}
