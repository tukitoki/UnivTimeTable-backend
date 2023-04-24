package ru.vsu.cs.timetable.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.vsu.cs.timetable.dto.group.CreateGroupRequest;
import ru.vsu.cs.timetable.dto.group.GroupDto;
import ru.vsu.cs.timetable.dto.group.GroupPageDto;
import ru.vsu.cs.timetable.dto.group.ShowCreateGroupDto;
import ru.vsu.cs.timetable.dto.page.SortDirection;

@Tag(name = "Group API", description = "API для работы с группами факульетов")
@SecurityRequirement(name = "JWT auth")
public interface GroupApi {

    @Operation(
            summary = "Получение списка групп с фильтрацией и поиском"
    )
    GroupPageDto getFacultyGroups(
            @Parameter(description = "Номер страницы")
            int pageNumber,
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

    @Operation(
            summary = "Создание группы конкретного факульета"
    )
    void createGroup(
            @Parameter(description = "Параметры для создания группы")
            CreateGroupRequest createGroupRequest,
            @Parameter(description = "Id факультета, группа для которого создается")
            Long facultyId
    );

    @Operation(
            summary = "Показ информации для создания группы конкретного факульета"
    )
    ShowCreateGroupDto showCreateGroup(
            @Parameter(description = "Id факультета, группа для которого создается")
            Long facultyId
    );

    @Operation(
            summary = "Удаление группы конкретного факульета"
    )
    void deleteGroup(
            @Parameter(description = "Id группы для удаления")
            Long id
    );

    @Operation(
            summary = "Обновление группы конкретного факульета"
    )
    void updateGroup(
            @Parameter(description = "Измененная версия группы")
            GroupDto groupDto,
            @Parameter(description = "Id группы для обновления")
            Long id
    );
}
