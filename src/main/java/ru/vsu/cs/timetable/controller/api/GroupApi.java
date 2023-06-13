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
import ru.vsu.cs.timetable.exception.message.ErrorMessage;
import ru.vsu.cs.timetable.model.dto.group.GroupDto;
import ru.vsu.cs.timetable.model.dto.group.GroupPageDto;
import ru.vsu.cs.timetable.model.dto.page.SortDirection;

@AccessDeniedResponse
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Group API", description = "API для работы с группами факульетов")
public interface GroupApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат групп",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GroupPageDto.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Возвращает список групп с фильтрацией и поиском"
    )
    ResponseEntity<GroupPageDto> getFacultyGroups(
            @Parameter(description = "Номер страницы", example = "1")
            int currentPage,
            @Parameter(description = "Количество элементов на странице", example = "10")
            int pageSize,
            @Parameter(description = "Курс для фильтрации", example = "1")
            Integer course,
            @Parameter(description = "Номер группы для фильтрации", example = "1")
            Integer groupNumber,
            @Parameter(description = "Сортировка по курсу")
            SortDirection order,
            @Parameter(description = "Id факультета, группы которого нужны", example = "1")
            Long facultyId
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат группы",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GroupDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Группа по переданному id не была найдена",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Возвращает группу по id"
    )
    ResponseEntity<GroupDto> getGroupById(
            @Parameter(description = "Id группы", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Успешное создание группы"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = """
                            Такая группа на этом факультете уже существует, \t
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
                    description = "Староста для группы по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Создает группу конкретного факультета"
    )
    ResponseEntity<Void> createGroup(
            @Parameter(description = "Параметры для создания группы")
            GroupDto groupDto,
            @Parameter(description = "Id факультета, группа для которого создается", example = "1")
            Long facultyId
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное удаление группы"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Группа по указанному id не была найдена",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Удаляет группу по id"
    )
    ResponseEntity<Void> deleteGroup(
            @Parameter(description = "Id группы для удаления", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное обновление группы"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = """
                            Группа с таким номером и курсом на этом факультете уже существует, \t
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
                    description = "Староста для этой группы по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Обновляет группу по id"
    )
    ResponseEntity<Void> updateGroup(
            @Parameter(description = "Измененная версия группы")
            GroupDto groupDto,
            @Parameter(description = "Id группы для обновления", example = "1")
            Long id
    );
}
