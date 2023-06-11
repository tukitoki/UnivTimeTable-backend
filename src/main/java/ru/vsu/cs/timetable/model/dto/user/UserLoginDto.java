package ru.vsu.cs.timetable.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Информация для входа пользователя")
public class UserLoginDto {

    @NotNull
    @NotBlank
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    @Schema(description = "Почта пользователя", example = "helloworld@gmail.com")
    private String email;
    @NotNull
    @NotBlank
    @Schema(description = "Пароль пользователя", example = "helloWorld123B")
    private String password;
}
