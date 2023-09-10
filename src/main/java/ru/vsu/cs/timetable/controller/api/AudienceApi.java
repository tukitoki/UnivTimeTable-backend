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
import ru.vsu.cs.timetable.exception.message.ErrorMessage;
import ru.vsu.cs.timetable.model.dto.audience.AudienceDto;
import ru.vsu.cs.timetable.model.dto.audience.AudiencePageDto;
import ru.vsu.cs.timetable.model.dto.audience.AudienceResponse;
import ru.vsu.cs.timetable.model.dto.audience.CreateAudienceRequest;

import java.util.List;

@AccessDeniedResponse
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Audience API", description = "API для работы с аудиториями")
public interface AudienceApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат аудиторий",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AudiencePageDto.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Возвращает список аудиторий"
    )
    ResponseEntity<AudiencePageDto> getAudiencesByFaculty(
            @Parameter(description = "Номер страницы", example = "1")
            int currentPage,
            @Parameter(description = "Количество элементов на странице", example = "10")
            int pageSize,
            @Parameter(description = "Id факультета, аудитории которого нужны", example = "1")
            Long facultyId
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Успешное создание аудитории"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = """
                            Аудитория с таким номером уже существует, \t
                            Не пройдена валидация
                            """,
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
            summary = "Создает новую аудиторию факультета"
    )
    ResponseEntity<Void> createAudience(
            @Parameter(description = "Информация для создания аудитории")
            CreateAudienceRequest createAudienceRequest,
            @Parameter(description = "Id университета", example = "1")
            Long univId,
            @Parameter(description = "Id факультета", example = "1")
            Long facultyId
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат аудитории",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AudienceDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Аудитория по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение аудитории по id"
    )
    ResponseEntity<AudienceDto> getAudienceById(
            @Parameter(description = "Id аудитории", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное удаление аудитории"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Аудитория по переданному id не была найдена",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Удаляет аудиторию по id"
    )
    ResponseEntity<Void> deleteAudience(
            @Parameter(description = "Id аудитоии, которую нужно удалить", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное обновление аудитории"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = """
                            Аудитория с таким номером в этом вузе на этом факультете уже существует, \t
                            Не пройдена валидация
                            """,
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Аудитория по переданному id не была найдена",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )}
            )}
    )
    @Operation(
            summary = "Обновляет аудиотрию конкретного факультета"
    )
    ResponseEntity<Void> updateAudience(
            @Parameter(description = "Измененная версия аудитории")
            AudienceDto audienceDto,
            @Parameter(description = "Id аудитории, которую нужно обновить", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат аудиторий"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Переданного факультета по указанному id не существует",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Возвращает список аудиторий факультета"
    )
    ResponseEntity<List<AudienceResponse>> getAllAudiencesByFaculty(Long facultyId);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение инвентаря",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = String.class)
                                    )
                            )
                    }
            )
    })
    @Operation(
            summary = "Возвращает возможный инвентарь аудитории"
    )
    ResponseEntity<List<String>> getAvailableEquipments();
}
