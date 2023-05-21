package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.timetable.controller.api.UserApi;
import ru.vsu.cs.timetable.dto.user.UserDto;
import ru.vsu.cs.timetable.dto.user.CreateUserResponse;
import ru.vsu.cs.timetable.dto.user.ShowUserResponse;
import ru.vsu.cs.timetable.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@PreAuthorize("hasAuthority('CREATE_USER_AUTHORITY')")
@RestController
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    @GetMapping("/users")
    public ShowUserResponse getAllUsers(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) List<String> universities,
            @RequestParam(required = false) List<String> roles,
            @RequestParam(required = false) List<String> cities,
            @RequestParam(required = false) String name
    ) {
        return userService.getAllUsers(currentPage, pageSize, universities, roles, cities, name);
    }

    @Override
    @GetMapping("/user/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserDtoById(id);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user/create")
    public void createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
    }

    @Override
    @GetMapping("/user/create")
    public CreateUserResponse showCreateUser() {
        return userService.showCreateUser();
    }

    @Override
    @PutMapping("/user/{id}")
    public void updateUser(@RequestBody UserDto userDto, @PathVariable Long id) {
        userService.updateUser(userDto, id);
    }

    @Override
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
