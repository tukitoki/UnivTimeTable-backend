package ru.vsu.cs.timetable.controller.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Request API", description = "API для работы с заявками преподавателей")
@SecurityRequirement(name = "JWT auth")
public interface RequestApi {

    void sendRequest();

    void showSendRequest();

    void moveClass();

    void showMoveClass();
}
