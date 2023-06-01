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
import ru.vsu.cs.timetable.dto.page.SortDirection;
import ru.vsu.cs.timetable.dto.university.UniversityDto;
import ru.vsu.cs.timetable.dto.university.UniversityPageDto;
import ru.vsu.cs.timetable.exception.message.ErrorMessage;

@AccessDeniedResponse
@Tag(name = "University API", description = "API для работы с университетами")
@SecurityRequirement(name = "bearer-key")
public interface UniversityApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение университетов",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = UniversityPageDto.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение списка университетов с фильтрацией и поиском"
    )
    ResponseEntity<UniversityPageDto> getAllUniversities(
            @Parameter(description = "Номер страницы")
            int currentPage,
            @Parameter(description = "Количество элементов на странице")
            int pageSize,
            @Parameter(description = "Имя университета для поиска")
            String universityName,
            @Parameter(description = "Тип сортировки по алфавиту")
            SortDirection order
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение университета",
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
            summary = "Получение университета по id"
    )
    ResponseEntity<UniversityDto> getUniversityById(
            @Parameter(description = "Id университета")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Успешное создание университета"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Такой университет уже был создан",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Создание университета"
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
                    description = "Такой университет уже был создан",
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
            summary = "Редактирование университета"
    )
    ResponseEntity<Void> updateUniversity(
            @Parameter(description = "Измененная версия университета")
            UniversityDto universityDto,
            @Parameter(description = "Id университета для редактирования")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное обновление университета"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Такой университет уже был создан",
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
            summary = "Удаление университета"
    )
    ResponseEntity<Void> deleteUniversity(
            @Parameter(description = "Id университета для удаления")
            Long id
    );
}
