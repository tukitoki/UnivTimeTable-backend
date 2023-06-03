package ru.vsu.cs.timetable.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.cs.timetable.entity.Group;
import ru.vsu.cs.timetable.entity.User;
import ru.vsu.cs.timetable.entity.enums.UserRole;
import ru.vsu.cs.timetable.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private User user1;
    @Mock
    private Group group1;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        group1.setId(1L);
        group1.setGroupNumber(1);
        group1.setStudentsAmount(1);
        user1.setCity("Воронеж");
        user1.setEmail("ivan@mail.ru");
        user1.setFullName("Иванов Иван Иванович");
        user1.setId(121311342L);
        user1.setUsername("ivan");
        user1.setRole(UserRole.valueOf("HEADMAN"));
        user1.setPassword("password");
        user1.setGroup(group1);
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
//        when(userRepository.delete(user1).thenReturn);

//        User userToCompare = userServiceImpl.deleteUser(1213113421L);
//        userServiceImpl.deleteUser(1L);

//        assert(group1.getStudentsAmount().equals(0));
//        assert(user1.());

////        int rowCount = Ebean.find(User.class).where().eq("id", 1).findRowCount();

    }

    @Test
    void getUserById() {
        when(userRepository.findById(121311342L))
                .thenReturn(Optional.of(user1));

        User userToCompare = userServiceImpl.getUserById(121311342L);

        assert(userToCompare.equals(user1));
        assertThat(user1.getId()).isNotNull();
//        userServiceImpl.createUser(userDto);
//        Optional<User> user2 = userRepository.findById(1213113421L);
    }

    @Test
    void getUserByUsername() {
        when(userRepository.findByUsername("ivan"))
                .thenReturn(Optional.of(user1));

        User userToCompare = userServiceImpl.getUserByUsername("ivan");

        assert(userToCompare.equals(user1));
    }

    @Test
    void getUserByEmail() {
        when(userRepository.findByEmail("ivan@mail.ru"))
                .thenReturn(Optional.of(user1));

        User userToCompare = userServiceImpl.getUserByEmail("ivan@mail.ru");

        assert(userToCompare.equals(user1));
    }
}

