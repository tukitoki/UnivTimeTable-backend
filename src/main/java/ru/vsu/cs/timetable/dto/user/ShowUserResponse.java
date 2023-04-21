package ru.vsu.cs.timetable.dto.user;

import ru.vsu.cs.timetable.dto.page.PageModel;

import java.util.List;

public class ShowUserResponse {

    private PageModel<UserDto> usersPage;
    private List<String> roles;
    private List<String> universities;
}
