package ru.vsu.cs.timetable.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import ru.vsu.cs.timetable.config.swagger.annotation.AccessDeniedResponse;
import ru.vsu.cs.timetable.config.swagger.annotation.IncorrectUsernameResponse;
import ru.vsu.cs.timetable.model.dto.TimetableResponse;
import ru.vsu.cs.timetable.exception.message.ErrorMessage;

@AccessDeniedResponse
@IncorrectUsernameResponse
@Tag(name = "Timetable API", description = "API для работы с расписанием")
@SecurityRequirement(name = "bearer-key")
public interface TimetableApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная отправка расписания",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TimetableResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Расписание ещё не было составлено",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение расписания конкретным пользователем"
    )
    ResponseEntity<TimetableResponse> getTimetable(
            @Parameter(hidden = true)
            Authentication authentication
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная передача файла для скачивания",
                    content = {
                            @Content(
                                    mediaType = "application/octet-stream",
                                    schema = @Schema(example = "timetable.xslx")
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Расписание ещё не было составлено",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Скачивание расписания"
    )
    ResponseEntity<Void> downloadTimetable(
            @Parameter(hidden = true)
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            Authentication authentication
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Расписание начало составляться"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Расписание не может быть составлено, \t\n" +
                            "Расписание уже было составлено",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Составление расписания"
    )
    ResponseEntity<Void> makeTimetable(
            @Parameter(hidden = true)
            Authentication authentication
    );
}
