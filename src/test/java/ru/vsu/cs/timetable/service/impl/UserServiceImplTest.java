package ru.vsu.cs.timetable.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.cs.timetable.dto.user.UserDto;
import ru.vsu.cs.timetable.entity.Group;
import ru.vsu.cs.timetable.entity.User;
import ru.vsu.cs.timetable.entity.enums.UserRole;
import ru.vsu.cs.timetable.mapper.UserMapper;
import ru.vsu.cs.timetable.repository.GroupRepository;
import ru.vsu.cs.timetable.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private UserMapper userMapper = new UserMapper();
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    private final UserDto userDto = new UserDto(121311342L, "HEADMAN", "Иванов Иван Иванович", "ivan", "ivan@mail.ru",
                                                "password","Воронеж", null, null, null);
    private Group group = new Group();
    private User user = new User();

    @BeforeEach
    void setUp() {
        group.setId(1L);
        group.setGroupNumber(1);
        group.setStudentsAmount(1);

        user.setCity("Воронеж");
        user.setEmail("ivan@mail.ru");
        user.setFullName("Иванов Иван Иванович");
        user.setId(121311342L);
        user.setUsername("ivan");
        user.setRole(UserRole.valueOf("HEADMAN"));
        user.setPassword("password");
        user.setGroup(group);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void getAllUsers() {
    }

    @Test
    void getUserDtoById() {
        when(userRepository.findById(121311342L))
                .thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).
                thenReturn(userDto);

        UserDto userDto1 = userServiceImpl.getUserDtoById(121311342L);

        assertThat(userDto1.getId()).isNotNull();
        assert(userDto1.getId().equals(121311342L));
        assert(userDto1.equals(userDto));
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
        User userToDelete = new User();
        userToDelete.setCity("Воронеж");
        userToDelete.setEmail("petr@mail.ru");
        userToDelete.setFullName("Петров Пётр Петрович");
        userToDelete.setId(1L);
        userToDelete.setUsername("petr");
        userToDelete.setRole(UserRole.valueOf("HEADMAN"));
        userToDelete.setPassword("password");
        userToDelete.setGroup(group);
        group.setStudentsAmount(2);

        assert(group.getStudentsAmount().equals(2));

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(userToDelete));
        userServiceImpl.deleteUser(1L);

        assert(group.getStudentsAmount().equals(1));
    }

    @Test
    void getUserById() {
        when(userRepository.findById(121311342L))
                .thenReturn(Optional.of(user));

        User userToCompare = userServiceImpl.getUserById(121311342L);

        assertThat(user.getFullName()).isNotNull();
        assert(userToCompare.getFullName().equals("Иванов Иван Иванович"));
        assert(userToCompare.equals(user));
    }

    @Test
    void getUserByUsername() {
        when(userRepository.findByUsername("ivan"))
                .thenReturn(Optional.of(user));

        User userToCompare = userServiceImpl.getUserByUsername("ivan");

        assertThat(user.getUsername()).isNotNull();
        assert(userToCompare.getUsername().equals("ivan"));
        assert(userToCompare.equals(user));

    }

    @Test
    void getUserByEmail() {
        when(userRepository.findByEmail("ivan@mail.ru"))
                .thenReturn(Optional.of(user));

        User userToCompare = userServiceImpl.getUserByEmail("ivan@mail.ru");

        assertThat(user.getEmail()).isNotNull();
        assert(userToCompare.getEmail().equals("ivan@mail.ru"));
        assert(userToCompare.equals(user));
    }
}