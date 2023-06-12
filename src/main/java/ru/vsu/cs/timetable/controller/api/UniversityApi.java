package ru.vsu.cs.timetable.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import ru.vsu.cs.timetable.config.swagger.annotation.AccessDeniedResponse;
import ru.vsu.cs.timetable.model.dto.page.SortDirection;
import ru.vsu.cs.timetable.model.dto.university.UniversityDto;
import ru.vsu.cs.timetable.model.dto.university.UniversityPageDto;
import ru.vsu.cs.timetable.exception.message.ErrorMessage;

@AccessDeniedResponse
@SecurityRequirement(name = "bearer-key")
@Tag(name = "University API", description = "API для работы с университетами")
public interface UniversityApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат университетов",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = UniversityPageDto.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Возвращает список университетов с фильтрацией и поиском"
    )
    ResponseEntity<UniversityPageDto> getAllUniversities(
            @Parameter(description = "Номер страницы", example = "1")
            int currentPage,
            @Parameter(description = "Количество элементов на странице", example = "10")
            int pageSize,
            @Parameter(description = "Имя университета для поиска", example = "Воронежский государственный университет")
            String universityName,
            @Parameter(description = "Сортировка по алфавиту")
            SortDirection order
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат университета",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = UniversityDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Университет по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Возвращает университет по id"
    )
    ResponseEntity<UniversityDto> getUniversityById(
            @Parameter(description = "Id университета", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Успешное создание университета"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Такой университет уже существует",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Создает университет"
    )
    ResponseEntity<Void> createUniversity(
            @Parameter(description = "Параметры для создания университета")
            UniversityDto createUnivRequest
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное обновление университета"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Такой университет уже существует",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Университет по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Обновляет университет"
    )
    ResponseEntity<Void> updateUniversity(
            @Parameter(description = "Измененная версия университета")
            UniversityDto universityDto,
            @Parameter(description = "Id университета для редактирования", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное удаление университета"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Университет по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Удаляет университет"
    )
    ResponseEntity<Void> deleteUniversity(
            @Parameter(description = "Id университета для удаления", example = "1")
            Long id
    );
}
