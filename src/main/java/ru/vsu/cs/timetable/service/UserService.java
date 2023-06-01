package ru.vsu.cs.timetable.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.vsu.cs.timetable.dto.user.UserDto;
import ru.vsu.cs.timetable.dto.user.CreateUserResponse;
import ru.vsu.cs.timetable.dto.user.UserPageDto;
import ru.vsu.cs.timetable.dto.user.UserResponse;
import ru.vsu.cs.timetable.entity.User;
import ru.vsu.cs.timetable.entity.enums.UserRole;

import java.util.List;

@Validated
public interface UserService {

    UserPageDto getAllUsers(int currentPage, int pageSize, String university,
                            UserRole role, String city, String name);

    List<UserResponse> getFreeHeadmenByFaculty(@NotNull Long facultyId);

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
