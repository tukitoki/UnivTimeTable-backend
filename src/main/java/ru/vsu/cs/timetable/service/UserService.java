package ru.vsu.cs.timetable.service;

import org.springframework.validation.annotation.Validated;
import ru.vsu.cs.timetable.dto.user.UserDto;
import ru.vsu.cs.timetable.dto.user.CreateUserResponse;
import ru.vsu.cs.timetable.dto.user.ShowUserResponse;
import ru.vsu.cs.timetable.model.User;

import java.util.List;

@Validated
public interface UserService {

    ShowUserResponse getAllUsers(int pageNumber, int pageSize, List<String> universities,
                                 List<String> roles, List<String> cities, String name);

    UserDto getUserDtoById(Long id);

    void createUser(UserDto userDto);

    CreateUserResponse showCreateUser();

    void updateUser(UserDto userDto, Long id);

    void deleteUser(Long id);

    User getUserById(Long id);

    User getUserByUsername(String username);
}
