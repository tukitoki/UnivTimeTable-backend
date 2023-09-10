package ru.vsu.cs.timetable.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.vsu.cs.timetable.logic.service.FacultyService;
import ru.vsu.cs.timetable.logic.service.GroupService;
import ru.vsu.cs.timetable.logic.service.UniversityService;
import ru.vsu.cs.timetable.logic.service.impl.UserServiceImpl;
import ru.vsu.cs.timetable.model.dto.user.UserDto;
import ru.vsu.cs.timetable.model.entity.Faculty;
import ru.vsu.cs.timetable.model.entity.Group;
import ru.vsu.cs.timetable.model.entity.University;
import ru.vsu.cs.timetable.model.entity.User;
import ru.vsu.cs.timetable.model.enums.UserRole;
import ru.vsu.cs.timetable.model.mapper.UserMapper;
import ru.vsu.cs.timetable.repository.GroupRepository;
import ru.vsu.cs.timetable.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private UniversityService universityService;
    @Mock
    private GroupService groupService;
    @Mock
    private FacultyService facultyService;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserRole userRole;
    private UserDto userDto;
    private Group group;
    private User user;
    private University universityVSU;
    private Faculty facultyFKN;
    private Faculty facultyPMM;


    @BeforeEach
    void setUp() {
        universityVSU = new University();
        facultyFKN = new Faculty();
        facultyPMM = new Faculty();
        user = new User();
        group = new Group();
        userRole = UserRole.HEADMAN;
        userDto = new UserDto(121311342L, userRole, "Иванов Иван Иванович", "ivan", "ivan@mail.ru",
                "Воронеж", "password", null, null, null);

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

        universityVSU.setId(1L);
        universityVSU.setCity("Воронеж");
        universityVSU.setName("ВГУ");

        facultyFKN.setId(1L);
        facultyFKN.setName("ФКН");
        facultyFKN.setUniversity(universityVSU);

        facultyPMM.setId(2L);
        facultyPMM.setName("ПММ");
        facultyPMM.setUniversity(universityVSU);
    }

    @Test
    @DisplayName("Should successfully find user DTO by id")
    void getUserDtoById() {
        when(userRepository.findById(121311342L))
                .thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).
                thenReturn(userDto);

        UserDto userDto1 = userServiceImpl.getUserDtoById(121311342L);

        assertThat(userDto1.getId()).isNotNull();
        assertEquals(userDto1.getId(), 121311342L);
        assertEquals(userDto1, userDto);
    }

    @Test
    @DisplayName("Should successfully create user")
    void createUser() {
        UserDto userToCreateDto = new UserDto(4L, userRole, "Семёнов Артём Валерьевич", "Artem", "artem@mail.ru", "Воронеж",
                "password", 1L, 1L, 1L);

        when(groupService.findGroupById(1L)).
                thenReturn(group);
        when(facultyService.findFacultyById(1L)).
                thenReturn(facultyFKN);
        when(universityService.findUnivById(1L)).
                thenReturn(universityVSU);
        when(passwordEncoder.encode("password")).
                thenReturn("password");

        User userToCreate = new User();
        userToCreate.setCity("Воронеж");
        userToCreate.setEmail("artem@mail.ru");
        userToCreate.setFullName("Семёнов Артём Валерьевич");
        userToCreate.setId(4L);
        userToCreate.setUsername("Artem");
        userToCreate.setRole(UserRole.valueOf("HEADMAN"));
        userToCreate.setPassword("password");
        userToCreate.setFaculty(facultyFKN);
        userToCreate.setGroup(group);
        userToCreate.setUniversity(universityVSU);

        when(userMapper.toEntity(userToCreateDto, universityVSU, group, facultyFKN, userToCreate.getPassword())).
                thenReturn(userToCreate);
        when(userRepository.findById(4L)).
                thenReturn(Optional.of(userToCreate));
        when(userRepository.save(userToCreate)).
                thenReturn(userToCreate);

        userServiceImpl.createUser(userToCreateDto);
        User createdUser = userServiceImpl.getUserById(4L);

        assertEquals(createdUser.getFullName(), "Семёнов Артём Валерьевич");
        assertEquals(createdUser.getEmail(), "artem@mail.ru");
        assertEquals(createdUser.getUsername(), "Artem");
        assertEquals(createdUser.getFaculty().getName(), "ФКН");
    }

    @Test
    @DisplayName("Should successfully update user")
    void updateUser() {
        User userToUpdate = new User();
        userToUpdate.setCity("Воронеж");
        userToUpdate.setEmail("petraf@mail.ru");
        userToUpdate.setFullName("Петров Пётр Петрович");
        userToUpdate.setId(3L);
        userToUpdate.setUsername("Petro");
        userToUpdate.setRole(UserRole.valueOf("HEADMAN"));
        userToUpdate.setPassword("password");
        userToUpdate.setFaculty(facultyPMM);
        userToUpdate.setGroup(group);

        assertEquals(userToUpdate.getFullName(), "Петров Пётр Петрович");
        assertEquals(userToUpdate.getEmail(), "petraf@mail.ru");
        assertEquals(userToUpdate.getUsername(), "Petro");
        assertEquals(userToUpdate.getFaculty().getName(), "ПММ");

        UserDto newUserDto = new UserDto(3L, userRole, "Семёнов Артём Валерьевич", "semen", "semen@mail.ru", "Воронеж",
                "password", 1L, 1L, 1L);
        User newUser = new User();
        newUser.setCity("Воронеж");
        newUser.setEmail("semen@mail.ru");
        newUser.setFullName("Семёнов Артём Валерьевич");
        newUser.setId(3L);
        newUser.setUsername("semen");
        newUser.setRole(UserRole.valueOf("HEADMAN"));
        newUser.setPassword("password");
        newUser.setFaculty(facultyFKN);
        newUser.setGroup(group);

        when(userRepository.findById(3L))
                .thenReturn(Optional.of(userToUpdate));
        when(userRepository.findByUsername("semen"))
                .thenReturn(Optional.of(userToUpdate));
        when(userRepository.findByEmail("semen@mail.ru"))
                .thenReturn(Optional.of(userToUpdate));

        when(groupService.findGroupById(1L)).
                thenReturn(group);
        when(facultyService.findFacultyById(1L)).
                thenReturn(facultyFKN);
        when(universityService.findUnivById(1L)).
                thenReturn(universityVSU);
        when(passwordEncoder.encode("password")).
                thenReturn("password");

        when(userMapper.toEntity(newUserDto, universityVSU, group, facultyFKN, userToUpdate.getPassword())).
                thenReturn(newUser);

        userServiceImpl.updateUser(newUserDto, 3L);

        assertEquals(userToUpdate.getFullName(), "Семёнов Артём Валерьевич");
        assertEquals(userToUpdate.getEmail(), "semen@mail.ru");
        assertEquals(userToUpdate.getUsername(), "semen");
        assertEquals(userToUpdate.getFaculty().getName(), "ФКН");
    }

    @Test
    @DisplayName("Should successfully delete user")
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

        assertEquals(group.getStudentsAmount(), 2);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(userToDelete));
        userServiceImpl.deleteUser(1L, "");

        assertEquals(group.getStudentsAmount(), 1);
    }

    @Test
    @DisplayName("Should successfully find user by id")

    void getUserById() {
        when(userRepository.findById(121311342L))
                .thenReturn(Optional.of(user));

        User userToCompare = userServiceImpl.getUserById(121311342L);

        assertThat(userToCompare.getFullName()).isNotNull();
        assertEquals(userToCompare.getFullName(), "Иванов Иван Иванович");
        assertEquals(userToCompare, (user));
    }

    @Test
    @DisplayName("Should successfully find user by username")
    void getUserByUsername() {
        when(userRepository.findByUsername("ivan"))
                .thenReturn(Optional.of(user));

        User userToCompare = userServiceImpl.getUserByUsername("ivan");

        assertThat(userToCompare.getUsername()).isNotNull();
        assertEquals(userToCompare.getUsername(), "ivan");
        assertEquals(userToCompare, user);
    }

    @Test
    @DisplayName("Should successfully find user by email")
    void getUserByEmail() {
        when(userRepository.findByEmail("ivan@mail.ru"))
                .thenReturn(Optional.of(user));

        User userToCompare = userServiceImpl.getUserByEmail("ivan@mail.ru");

        assertThat(userToCompare.getEmail()).isNotNull();
        assertEquals(userToCompare.getEmail(), "ivan@mail.ru");
        assertEquals(userToCompare, user);
    }
}