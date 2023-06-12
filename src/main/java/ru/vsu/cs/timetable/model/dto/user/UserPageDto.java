package ru.vsu.cs.timetable.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.page.PageModel;
import ru.vsu.cs.timetable.model.enums.UserRole;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@Schema(description = "Страница с пользователями с информацией о ролях, университетах и городах")
public class UserPageDto {

    @Schema(description = "Страница с пользователями")
    private PageModel<UserResponse> usersPage;
    @Schema(description = "Список ролей", example = "[\"Преподаватель\", \"Староста\", \"Администратор\"]")
    private List<UserRole> roles;
    @Schema(description = "Список университетов", example = "[\"Воронежский государственный университет\", " +
            "\"Воронежский государственный технический университет\"]")
    private List<String> universities;
    @Schema(description = "Список городов", example = "[\"Воронеж\", \"Москва\"]")
    private List<String> cities;
}
