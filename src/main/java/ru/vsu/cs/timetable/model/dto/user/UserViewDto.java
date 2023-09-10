package ru.vsu.cs.timetable.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.enums.UserRole;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@Schema(description = "Пользователи с информацией о ролях, университетах и городах")
public class UserViewDto {

    @Schema(description = "Пользователи")
    private List<UserResponse> users;
    @Schema(description = "Список ролей", example = "[\"Преподаватель\", \"Староста\", \"Администратор\"]")
    private List<UserRole> roles;
    @Schema(description = "Список университетов", example = "[\"Воронежский государственный университет\", " +
            "\"Воронежский государственный технический университет\"]")
    private List<String> universities;
    @Schema(description = "Список городов", example = "[\"Воронеж\", \"Москва\"]")
    private List<String> cities;
}
