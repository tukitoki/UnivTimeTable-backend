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
import ru.vsu.cs.timetable.exception.message.ErrorMessage;
import ru.vsu.cs.timetable.model.dto.TimetableResponse;

@AccessDeniedResponse
@IncorrectUsernameResponse
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Timetable API", description = "API для работы с расписанием")
public interface TimetableApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат расписания",
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
            summary = "Возвращает расписание конкретного пользователя"
    )
    ResponseEntity<TimetableResponse> getTimetable(
            @Parameter(hidden = true)
            Authentication authentication
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат файла для скачивания",
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
            summary = "Возвращает файл с расписанием в формате .xslx"
    )
    ResponseEntity<?> downloadTimetable(
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
            summary = "Начинает составлять расписание"
    )
    ResponseEntity<Void> makeTimetable(
            @Parameter(hidden = true)
            Authentication authentication
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Расписание было успешно сброшено"
            )
    })
    @Operation(
            summary = "Сбрасывает расписание"
    )
    ResponseEntity<Void> resetTimetable(
            @Parameter(hidden = true)
            Authentication authentication
    );
}
