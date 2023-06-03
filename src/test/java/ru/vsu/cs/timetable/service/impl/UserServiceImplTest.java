package ru.vsu.cs.timetable.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import ru.vsu.cs.timetable.controller.UserController;
import ru.vsu.cs.timetable.dto.user.UserDto;
import ru.vsu.cs.timetable.entity.User;
import ru.vsu.cs.timetable.entity.enums.UserRole;
import ru.vsu.cs.timetable.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

/*
    @Autowired
    private MockMvc mockMvc;
*/

    @Mock
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserController userController;

    @Mock
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto(1213113421L, "HEADMAN", "Иванов Иван Иванович", "ivan", "ivan@mail.ru",
                "password","Воронеж", null, null, null);
        userServiceImpl.createUser(userDto);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void getUserDtoById() {
    }

    @Test
    void createUser() {
    }

    @Test
    void showCreateUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUserById() {

    }

    @Test
    void getUserByUsername() {
    }

    @Test
    void getUserByEmail() {
    }
}