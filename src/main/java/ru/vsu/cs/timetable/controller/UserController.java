package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.timetable.controller.api.UserApi;
import ru.vsu.cs.timetable.dto.user.CreateUserRequest;
import ru.vsu.cs.timetable.dto.user.CreateUserResponse;
import ru.vsu.cs.timetable.dto.user.ShowUserResponse;
import ru.vsu.cs.timetable.dto.user.UserDto;
import ru.vsu.cs.timetable.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    @GetMapping("/users")
    public ShowUserResponse getAllUsers(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) List<String> universities,
            @RequestParam(required = false) List<String> roles,
            @RequestParam(required = false) List<String> cities,
            @RequestParam(required = false) String name
    ) {
        return null;
    }

    @Override
    @PostMapping("/user/create")
    public void createUser(@RequestBody CreateUserRequest createUserRequest) {
        userService.createUser(createUserRequest);
    }

    @Override
    @GetMapping("/user/create")
    public CreateUserResponse showCreateUser() {
        return null;
    }

    @Override
    @PutMapping("/user/{id}")
    public void updateUser(@RequestBody UserDto userDto, @PathVariable Long id) {

    }

    @Override
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id) {

    }
}
