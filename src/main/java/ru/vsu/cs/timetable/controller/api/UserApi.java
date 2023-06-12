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
import ru.vsu.cs.timetable.model.dto.user.CreateUserResponse;
import ru.vsu.cs.timetable.model.dto.user.UserDto;
import ru.vsu.cs.timetable.model.dto.user.UserPageDto;
import ru.vsu.cs.timetable.model.dto.user.UserResponse;
import ru.vsu.cs.timetable.model.enums.UserRole;
import ru.vsu.cs.timetable.exception.message.ErrorMessage;

import java.util.List;

@AccessDeniedResponse
@SecurityRequirement(name = "bearer-key")
@Tag(name = "User API", description = "API для работы с пользователем")
public interface UserApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат пользователей",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = UserPageDto.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Возвращает список пользователей с фильтрацией и поиском"
    )
    ResponseEntity<UserPageDto> getAllUsers(
            @Parameter(description = "Номер страницы", example = "1")
            int currentPage,
            @Parameter(description = "Количество элементов на странице", example = "10")
            int pageSize,
            @Parameter(description = "Вуз для фильтрации", example = "Воронежский государственный университет")
            String universities,
            @Parameter(description = "Роль для фильтрации", example = "LECTURER")
            UserRole role,
            @Parameter(description = "Город для фильтрации", example = "Воронеж")
            String city,
            @Parameter(description = "ФИО для поиска", example = "Андреев Андрей Андреевич")
            String name
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат свободных старост факультета",
                    content = {
                            @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))
                            )
                    }
            )
    })
    @Operation(
            summary = "Возвращает свободных старост факультета"
    )
    ResponseEntity<List<UserResponse>> getFreeHeadmenByFaculty(
            @Parameter(description = "Id факультета", example = "1")
            Long facultyId
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат пользователя",
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
            summary = "Возвращает пользователя по id"
    )
    ResponseEntity<UserDto> getUserById(
            @Parameter(description = "Id пользователя", example = "1")
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
            summary = "Создает пользователя"
    )
    ResponseEntity<Void> createUser(
            @Parameter(description = "Параметры создания пользователя")
            UserDto userDto
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат информации для создании пользователя",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = CreateUserResponse.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Возвращает информацию для создания пользователя"
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
                            Пользователь с таким username уже существует, \t
                            Пользователь с таким email уже существует
                            """,
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Обновляет пользователя"
    )
    ResponseEntity<Void> updateUser(
            @Parameter(description = "Новые характеристики пользователя")
            UserDto userDto,
            @Parameter(description = "Id пользователя для редактирования", example = "1")
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
            summary = "Удаляет пользователя"
    )
    ResponseEntity<Void> deleteUser(
            @Parameter(description = "Id пользователя для удаления", example = "1")
            Long id
    );
}
