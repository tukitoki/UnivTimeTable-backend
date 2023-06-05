package ru.vsu.cs.timetable.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import ru.vsu.cs.timetable.config.swagger.annotation.AccessDeniedResponse;
import ru.vsu.cs.timetable.dto.audience.CreateAudienceRequest;
import ru.vsu.cs.timetable.exception.message.ErrorMessage;

import java.util.List;

@AccessDeniedResponse
@Tag(name = "Audience API", description = "API для работы с аудиториями")
@SecurityRequirement(name = "bearer-key")
public interface AudienceApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Успешное создание аудитории"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Аудитория с таким номером уже была создана",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Переданный инвентарь не существует в базе",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Создание аудитории конкретного факультета"
    )
    ResponseEntity<Void> createAudience(
            @Parameter(description = "Создание аудитории конкретного факультета")
            CreateAudienceRequest createAudienceRequest,
            @Parameter(description = "Id университета")
            Long univId,
            @Parameter(description = "Id факультета")
            Long facultyId
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Инвентарь успешно получен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = String.class))
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение возможного инвентаря в аудитории"
    )
    ResponseEntity<List<String>> getAvailableEquipments();
}
