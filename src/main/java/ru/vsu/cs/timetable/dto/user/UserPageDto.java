package ru.vsu.cs.timetable.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.dto.page.PageModel;
import ru.vsu.cs.timetable.entity.enums.UserRole;

import java.util.List;

@Setter
@Getter
@SuperBuilder
public class UserPageDto {

    private PageModel<UserResponse> usersPage;
    private List<UserRole> roles;
    private List<String> universities;
    private List<String> cities;
}