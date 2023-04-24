package ru.vsu.cs.timetable.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.vsu.cs.timetable.dto.TimetableResponse;

@Tag(name = "Timetable API", description = "API для работы с расписанием")
@SecurityRequirement(name = "JWT auth")
public interface TimetableApi {

    @Operation(
            summary = "Получение расписания конкретным пользователем"
    )
    TimetableResponse getTimetable();

    @Operation(
            summary = ""
    )
    void downloadTimetable();

    @Operation(
            summary = ""
    )
    void makeTimetable();
}
