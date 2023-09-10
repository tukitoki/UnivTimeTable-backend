package ru.vsu.cs.timetable.logic.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.vsu.cs.timetable.model.dto.user.*;
import ru.vsu.cs.timetable.model.entity.User;
import ru.vsu.cs.timetable.model.enums.UserRole;

import java.util.List;

@Validated
public interface UserService {

    UserViewDto getAllUsersV2(String university, UserRole role,
                              String city, String name);

    UserPageDto getAllUsers(int currentPage, int pageSize, String university,
                            UserRole role, String city, String name);

    List<UserResponse> getFreeHeadmenByFaculty(@NotNull Long facultyId);

    UserDto getUserDtoById(@NotNull Long id);

    void createUser(@NotNull @Valid UserDto userDto);

    CreateUserResponse showCreateUser();

    void updateUser(@NotNull @Valid UserDto userDto,
                    @NotNull Long id);

    void deleteUser(@NotNull Long id, @NotNull String username);

    User getUserById(@NotNull Long id);

    User getUserByUsername(@NotNull String username);

    User getUserByEmail(@NotNull String email);
}
