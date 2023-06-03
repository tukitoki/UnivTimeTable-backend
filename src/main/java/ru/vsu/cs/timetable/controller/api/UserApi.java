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
import ru.vsu.cs.timetable.dto.user.CreateUserResponse;
import ru.vsu.cs.timetable.dto.user.UserDto;
import ru.vsu.cs.timetable.dto.user.UserPageDto;
import ru.vsu.cs.timetable.dto.user.UserResponse;
import ru.vsu.cs.timetable.entity.enums.UserRole;
import ru.vsu.cs.timetable.exception.message.ErrorMessage;

import java.util.List;

@AccessDeniedResponse
@Tag(name = "User API", description = "API для работы с пользователем")
@SecurityRequirement(name = "bearer-key")
public interface UserApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение пользователей",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = UserPageDto.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение списка пользователей с фильтрацией и поиском"
    )
    ResponseEntity<UserPageDto> getAllUsers(
            @Parameter(description = "Номер страницы")
            int currentPage,
            @Parameter(description = "Количество элементов на странице")
            int pageSize,
            @Parameter(description = "Вуз для фильтрации")
            String universities,
            @Parameter(description = "Роль для фильтрации")
            UserRole roles,
            @Parameter(description = "Город для фильтрации")
            String cities,
            @Parameter(description = "ФИО для поиска")
            String name
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение свободных старост факультета",
                    content = {
                            @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение свободных старост факультета"
    )
    ResponseEntity<List<UserResponse>> getFreeHeadmenByFaculty(
            @Parameter(description = "Id факультета")
            Long facultyId
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение пользователя",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь по переданному id не был найден",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получить пользователя по id"
    )
    ResponseEntity<UserDto> getUserById(
            @Parameter(description = "Id пользователя")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Успешное создание пользователя"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Пользователь с таким username уже присутствует, \t\n\"" +
                            "Пользователь с таким email уже присутствует",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Создание пользователя"
    )
    ResponseEntity<Void> createUser(
            @Parameter(description = "Параметры создания пользователя")
            UserDto userDto
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный показ информации для создании пользователя",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = CreateUserResponse.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Показ информации для создания пользователя"
    )
    ResponseEntity<CreateUserResponse> createUserInfo();

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное обновление пользователя"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = """
                            Пользователь с таким username уже присутствует, \t
                            Пользователь с таким email уже присутствует
                            """,
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Обновление пользователя"
    )
    ResponseEntity<Void> updateUser(
            @Parameter(description = "Новые характеристики пользователя")
            UserDto userDto,
            @Parameter(description = "Id пользователя для редактирования")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное удаление пользователя"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь по переданному id не был найден",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Удаление пользователя"
    )
    ResponseEntity<Void> deleteUser(
            @Parameter(description = "Id пользователя для удаления")
            Long id
    );
}
