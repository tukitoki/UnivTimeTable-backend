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
import ru.vsu.cs.timetable.model.dto.faculty.CreateFacultyRequest;
import ru.vsu.cs.timetable.model.dto.faculty.FacultyDto;
import ru.vsu.cs.timetable.model.dto.faculty.FacultyPageDto;
import ru.vsu.cs.timetable.model.dto.page.SortDirection;

import java.util.List;

@AccessDeniedResponse
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Faculty API", description = "API для работы с факультетами")
public interface FacultyApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат факультетов",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = FacultyPageDto.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Возвращает список факультетов с фильтрацией и поиском"
    )
    ResponseEntity<FacultyPageDto> getFacultiesByUniversity(
            @Parameter(description = "Номер страницы", example = "1")
            int currentPage,
            @Parameter(description = "Количество элементов на странице", example = "10")
            int pageSize,
            @Parameter(description = "Название факультета для поиска", example = "Факультет компьютерных наук")
            String name,
            @Parameter(description = "Сортировка по алфавиту")
            SortDirection order,
            @Parameter(description = "Id университета, факультеты которого нужны", example = "1")
            Long universityId
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат факультетов",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = FacultyDto.class)
                                    )
                            )
                    }
            )
    })
    @Operation(
            summary = "Возвращает список всех факультетов"
    )
    ResponseEntity<List<FacultyDto>> getFacultiesByUniversity(
            @Parameter(description = "Id университета, факультеты которого нужны", example = "1")
            Long universityId
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат факультета",
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
            summary = "Возвращает факультет по id"
    )
    ResponseEntity<FacultyDto> getFacultyById(
            @Parameter(description = "Id факультета", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Успешное создание факультета"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Такой факультет в этом университете уже существует",
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
            summary = "Создает факультет конкретного университета"
    )
    ResponseEntity<Void> createFaculty(
            @Parameter(description = "Параметры для создания факультета")
            CreateFacultyRequest createFacultyRequest,
            @Parameter(description = "Id университета, для которого создается факультет", example = "1")
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
            summary = "Удаляет факультет по id"
    )
    ResponseEntity<Void> deleteFaculty(
            @Parameter(description = "Id факультета, который нужно удалить", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное обновление факультета"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Факультет с таким именем уже существует",
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
            summary = "Обновляет факультета конкретного университета"
    )
    ResponseEntity<Void> updateFaculty(
            @Parameter(description = "Измененная версия факульета")
            FacultyDto facultyDto,
            @Parameter(description = "Id факультета, который нужно обновить", example = "1")
            Long id
    );
}
