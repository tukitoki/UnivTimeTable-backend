package ru.vsu.cs.timetable.controller.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Timetable API", description = "API для работы с расписанием")
@SecurityRequirement(name = "JWT auth")
public interface TimetableApi {

    void getTimetable();

    void downloadTimetable();

    void makeTimetable();
}
