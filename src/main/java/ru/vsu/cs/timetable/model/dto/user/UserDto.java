package ru.vsu.cs.timetable.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.enums.UserRole;

@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
@Schema(description = "Полная информация о пользователе")
public class UserDto {

    @Schema(description = "Id пользователя", example = "1")
    private Long id;
    @NotNull
    @Schema(description = "Роль пользователя", example = "LECTURER")
    private UserRole role;
    @NotNull
    @NotBlank
    @Schema(description = "Полное имя пользователя", example = "Андреев Андрей Андреевич")
    private String fullName;
    @NotNull
    @NotBlank
    @Schema(description = "Имя пользователя", example = "andreev_a_a")
    private String username;
    @NotNull
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    @Schema(description = "Почта пользователя", example = "andreev2001@gmail.com")
    private String email;
    @NotNull
    @NotBlank
    @Schema(description = "Город пользователя", example = "Воронеж")
    private String city;
    @Schema(description = "Пароль пользователя", example = "AndreY2001")
    private String password;
    @Schema(description = "Id университета", example = "1")
    private Long universityId;
    @Schema(description = "Id факультета", example = "1")
    private Long facultyId;
    @Schema(description = "Id группы", example = "1")
    private Long groupId;
}
