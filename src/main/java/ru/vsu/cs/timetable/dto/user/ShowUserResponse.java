package ru.vsu.cs.timetable.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.dto.page.PageModel;

import java.util.List;

@Setter
@Getter
@SuperBuilder
public class ShowUserResponse {

    private PageModel<UserDto> usersPage;
    private List<String> roles;
    private List<String> universities;
}
