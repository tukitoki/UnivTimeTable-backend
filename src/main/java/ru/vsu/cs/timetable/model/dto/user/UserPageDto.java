package ru.vsu.cs.timetable.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.page.PageModel;
import ru.vsu.cs.timetable.model.entity.enums.UserRole;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@Schema(description = "Страница с пользователями с информацией о ролях, университетах и городах")
public class UserPageDto {

    private PageModel<UserResponse> usersPage;
    private List<UserRole> roles;
    private List<String> universities;
    private List<String> cities;
}
