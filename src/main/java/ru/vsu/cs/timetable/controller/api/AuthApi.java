package ru.vsu.cs.timetable.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import ru.vsu.cs.timetable.model.dto.user.JwtDto;
import ru.vsu.cs.timetable.model.dto.user.UserLoginDto;
import ru.vsu.cs.timetable.exception.message.ErrorMessage;

@Tag(name = "Auth API", description = "API для входа в систему и refresh JWT-token")
public interface AuthApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Вход в аккаунт успешно выполнен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = JwtDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверные учетные данные пользователя",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь по переданному email не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(summary = "Возвращает jwt и refresh token's")
    ResponseEntity<JwtDto> loginUser(
            @Parameter(description = "Почта и пароль пользователя")
            UserLoginDto userLoginDto
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Refresh был успешно выполнен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = JwtDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Неверный username в refreshToken",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(summary = "Возвращает обновленные jwt и refresh token's")
    ResponseEntity<JwtDto> refreshToken(
            @Parameter(description = "Refresh токен")
            String refreshToken
    );
}
