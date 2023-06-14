package ru.vsu.cs.timetable.exception.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ErrorMessage(
        @Schema(description = "Код ошибки", example = "BAD_REQUEST")
        String code,
        @Schema(description = "Сообщение ошибки", example = "Faculty with given name at this university already present")
        String message
) {
}
