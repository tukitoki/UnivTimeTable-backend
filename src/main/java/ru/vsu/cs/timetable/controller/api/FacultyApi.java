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
import ru.vsu.cs.timetable.model.dto.faculty.CreateFacultyRequest;
import ru.vsu.cs.timetable.model.dto.faculty.FacultyDto;
import ru.vsu.cs.timetable.model.dto.faculty.FacultyPageDto;
import ru.vsu.cs.timetable.model.dto.page.SortDirection;
import ru.vsu.cs.timetable.exception.message.ErrorMessage;

import java.util.List;

@AccessDeniedResponse
@Tag(name = "Faculty API", description = "API для работы с факультетами")
@SecurityRequirement(name = "bearer-key")
public interface FacultyApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение факультетов",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = FacultyPageDto.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение списка факультетов с фильтрацией и поиском"
    )
    ResponseEntity<FacultyPageDto> getFacultiesByUniversity(
            @Parameter(description = "Номер страницы")
            int currentPage,
            @Parameter(description = "Количество элементов на странице")
            int pageSize,
            @Parameter(description = "Название факультета для поиска")
            String name,
            @Parameter(description = "Сортировка по алфавиту")
            SortDirection order,
            @Parameter(description = "Id университета, факультеты которого нужны")
            Long universityId
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение факультетов",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = FacultyPageDto.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение списка факультетов с фильтрацией и поиском"
    )
    ResponseEntity<List<FacultyDto>> getFacultiesByUniversity(
            @Parameter(description = "Id университета, факультеты которого нужны")
            Long universityId
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получние факультета",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = FacultyDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Факультет по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение факультета по id"
    )
    ResponseEntity<FacultyDto> getFacultyById(
            @Parameter(description = "Id факультета")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Успешное создание факультета"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Такой факультет в этом университете уже был создан",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Университет по переданному id для добавления факульета не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Создание факультета конкретного университета"
    )
    ResponseEntity<Void> createFaculty(
            @Parameter(description = "Параметры для создания факультета")
            CreateFacultyRequest createFacultyRequest,
            @Parameter(description = "Id университета, для которого создаются факультеты")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное удаление факультета"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Факультет по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Удаление факультета конкретного университета"
    )
    ResponseEntity<Void> deleteFaculty(
            @Parameter(description = "Id факультета, который нужно удалить")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное обновление факультета"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Такой факультет в этом университете уже был создан",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Факультет по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )}
            )}
    )
    @Operation(
            summary = "Обновление факультета конкретного университета"
    )
    ResponseEntity<Void> updateFaculty(
            @Parameter(description = "Измененная версия факульета")
            FacultyDto facultyDto,
            @Parameter(description = "Id факультета, который нужно обновить")
            Long id
    );
}
