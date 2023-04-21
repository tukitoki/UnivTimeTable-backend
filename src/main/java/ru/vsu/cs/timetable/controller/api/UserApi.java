package ru.vsu.cs.timetable.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.vsu.cs.timetable.dto.user.CreateUserRequest;
import ru.vsu.cs.timetable.dto.user.CreateUserResponse;
import ru.vsu.cs.timetable.dto.user.ShowUserResponse;
import ru.vsu.cs.timetable.dto.user.UserDto;

import java.util.List;

@Tag(name = "User API", description = "API для работы с пользователем")
@SecurityRequirement(name = "JWT auth")
public interface UserApi {

    @Operation(
            summary = "Получение списка пользователей с фильтрацией и поиском"
    )
    ShowUserResponse getAllUsers(
            @Parameter(description = "Номер страницы")
            int pageNumber,
            @Parameter(description = "Количество элементов на странице")
            int pageSize,
            @Parameter(description = "Список вузов для фильтрации")
            List<String> universities,
            @Parameter(description = "Список ролей для фильтрации")
            List<String> roles,
            @Parameter(description = "Список городов для фильтрации")
            List<String> cities,
            @Parameter(description = "ФИО для поиска")
            String name
    );

    @Operation(
            summary = "Создание пользователя конкретного университета"
    )
    void createUser(
            @Parameter(description = "Параметры создания пользователя")
            CreateUserRequest createUserRequest
    );

    @Operation(
            summary = "Показ страницы создания пользователя конкретного университета"
    )
    CreateUserResponse showCreateUser();

    @Operation(
            summary = "Создание пользователя конкретного университета"
    )
    void updateUser(
            @Parameter(description = "Id пользователя для редактирования")
            Long id,
            @Parameter(description = "Новые характеристики пользователя")
            UserDto userDto
    );

    @Operation(
            summary = "Создание пользователя конкретного университета"
    )
    void deleteUser(
            @Parameter(description = "Id пользователя для удаления")
            Long id
    );
}
