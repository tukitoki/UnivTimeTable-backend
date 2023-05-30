package ru.vsu.cs.timetable.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.vsu.cs.timetable.dto.user.UserDto;
import ru.vsu.cs.timetable.dto.user.CreateUserResponse;
import ru.vsu.cs.timetable.dto.user.ShowUserResponse;
import ru.vsu.cs.timetable.entity.User;

import java.util.List;

@Validated
public interface UserService {

    ShowUserResponse getAllUsers(int currentPage, int pageSize, List<String> universities,
                                 List<String> roles, List<String> cities, String name);

    UserDto getUserDtoById(@NotNull Long id);

    void createUser(@NotNull @Valid UserDto userDto);

    CreateUserResponse showCreateUser();

    void updateUser(@NotNull @Valid UserDto userDto,
                    @NotNull Long id);

    void deleteUser(@NotNull Long id);

    User getUserById(@NotNull Long id);

    User getUserByUsername(@NotNull String username);

    User getUserByEmail(@NotNull String email);
}
