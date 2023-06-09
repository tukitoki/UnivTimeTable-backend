package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.timetable.controller.api.UserApi;
import ru.vsu.cs.timetable.model.dto.user.CreateUserResponse;
import ru.vsu.cs.timetable.model.dto.user.UserDto;
import ru.vsu.cs.timetable.model.dto.user.UserPageDto;
import ru.vsu.cs.timetable.model.dto.user.UserResponse;
import ru.vsu.cs.timetable.model.entity.enums.UserRole;
import ru.vsu.cs.timetable.logic.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@PreAuthorize("hasAuthority('CREATE_USER_AUTHORITY')")
@RestController
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    @PreAuthorize("hasAuthority('CREATE_USER_AUTHORITY')")
    @GetMapping("/users")
    public ResponseEntity<UserPageDto> getAllUsers(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String university,
            @RequestParam(required = false) UserRole role,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String name
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAllUsers(currentPage, pageSize, university, role, city, name));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('CREATE_USER_AUTHORITY', 'CREATE_GROUP_AUTHORITY')")
    @GetMapping("/faculty/{facultyId}/headmen")
    public ResponseEntity<List<UserResponse>> getFreeHeadmenByFaculty(@PathVariable Long facultyId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getFreeHeadmenByFaculty(facultyId));
    }


    @Override
    @PreAuthorize("hasAuthority('CREATE_USER_AUTHORITY')")
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserDtoById(id));
    }

    @Override
    @PreAuthorize("hasAuthority('CREATE_USER_AUTHORITY')")
    @PostMapping("/user/create")
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    @PreAuthorize("hasAuthority('CREATE_USER_AUTHORITY')")
    @GetMapping("/user/create")
    public ResponseEntity<CreateUserResponse> createUserInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.showCreateUser());
    }

    @Override
    @PreAuthorize("hasAuthority('CREATE_USER_AUTHORITY')")
    @PutMapping("/user/{id}")
    public ResponseEntity<Void> updateUser(@RequestBody UserDto userDto, @PathVariable Long id) {
        userService.updateUser(userDto, id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    @PreAuthorize("hasAuthority('CREATE_USER_AUTHORITY')")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
