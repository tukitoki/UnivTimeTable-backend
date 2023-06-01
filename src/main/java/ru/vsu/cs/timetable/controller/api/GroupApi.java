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
import ru.vsu.cs.timetable.dto.group.GroupDto;
import ru.vsu.cs.timetable.dto.group.GroupPageDto;
import ru.vsu.cs.timetable.dto.page.SortDirection;
import ru.vsu.cs.timetable.exception.message.ErrorMessage;

@AccessDeniedResponse
@Tag(name = "Group API", description = "API для работы с группами факульетов")
@SecurityRequirement(name = "bearer-key")
public interface GroupApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получеие групп",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GroupPageDto.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение списка групп с фильтрацией и поиском"
    )
    ResponseEntity<GroupPageDto> getFacultyGroups(
            @Parameter(description = "Номер страницы")
            int currentPage,
            @Parameter(description = "Количество элементов на странице")
            int pageSize,
            @Parameter(description = "Курс для фильтрации")
            Integer course,
            @Parameter(description = "Номер группы для фильтрации")
            Integer groupNumber,
            @Parameter(description = "")
            SortDirection order,
            @Parameter(description = "Id факультета, группы которого нужны")
            Long facultyId
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение группы",
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
            summary = "Получение группы по id"
    )
    ResponseEntity<GroupDto> getGroupById(
            @Parameter(description = "Id группы")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Успешное создание группы"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Такая группа на этом факультете уже была создана",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Староста для этой группы по указанному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Создание группы конкретного факульета"
    )
    ResponseEntity<Void> createGroup(
            @Parameter(description = "Параметры для создания группы")
            GroupDto groupDto,
            @Parameter(description = "Id факультета, группа для которого создается")
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
            summary = "Удаление группы конкретного факульета"
    )
    ResponseEntity<Void> deleteGroup(
            @Parameter(description = "Id группы для удаления")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное обновление группы"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Такая группа на этом факультете уже была создана",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Староста для этой группы по указанному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Обновление группы конкретного факульета"
    )
    ResponseEntity<Void> updateGroup(
            @Parameter(description = "Измененная версия группы")
            GroupDto groupDto,
            @Parameter(description = "Id группы для обновления")
            Long id
    );
}
