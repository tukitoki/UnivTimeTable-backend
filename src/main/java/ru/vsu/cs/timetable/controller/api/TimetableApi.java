package ru.vsu.cs.timetable.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import ru.vsu.cs.timetable.dto.TimetableResponse;

@Tag(name = "Timetable API", description = "API для работы с расписанием")
@SecurityRequirement(name = "bearer-key")
public interface TimetableApi {

    @Operation(
            summary = "Получение расписания конкретным пользователем"
    )
    TimetableResponse getTimetable(
            @Parameter(hidden = true)
            Authentication authentication
    );

    @Operation(
            summary = "Скачивание расписания"
    )
    void downloadTimetable(
            @Parameter(hidden = true)
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            Authentication authentication
    );

    @Operation(
            summary = "Составление расписания"
    )
    void makeTimetable(
            @Parameter(hidden = true)
            Authentication authentication
    );
}
