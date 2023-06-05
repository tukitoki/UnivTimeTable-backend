package ru.vsu.cs.timetable.config.swagger.annotation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.vsu.cs.timetable.exception.message.ErrorMessage;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ApiResponse(
        responseCode = "403",
        description = "Недостаточно прав доступа для выполнения",
        content = {
                @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorMessage.class)
                )}
)
public @interface AccessDeniedResponse {
}
