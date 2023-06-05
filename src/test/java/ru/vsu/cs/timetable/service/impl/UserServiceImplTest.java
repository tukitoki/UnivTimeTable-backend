package ru.vsu.cs.timetable.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.vsu.cs.timetable.dto.user.UserDto;
import ru.vsu.cs.timetable.entity.Faculty;
import ru.vsu.cs.timetable.entity.Group;
import ru.vsu.cs.timetable.entity.University;
import ru.vsu.cs.timetable.entity.User;
import ru.vsu.cs.timetable.entity.enums.UserRole;
import ru.vsu.cs.timetable.mapper.UserMapper;
import ru.vsu.cs.timetable.repository.GroupRepository;
import ru.vsu.cs.timetable.repository.UserRepository;
import ru.vsu.cs.timetable.service.FacultyService;
import ru.vsu.cs.timetable.service.GroupService;
import ru.vsu.cs.timetable.service.UniversityService;

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
    private UserRole userRole = UserRole.HEADMAN;
    private final UserDto userDto = new UserDto(121311342L, userRole, "Иванов Иван Иванович", "ivan", "ivan@mail.ru",
            "Воронеж", "password", null, null, null);
    private Group group = new Group();
    private User user = new User();
    private University universityVSU = new University();
    private Faculty facultyFKN = new Faculty();
    private Faculty facultyPMM = new Faculty();


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

        assert(createdUser.getFullName().equals("Семёнов Артём Валерьевич"));
        assert(createdUser.getEmail().equals("artem@mail.ru"));
        assert(createdUser.getUsername().equals("Artem"));
        assert(createdUser.getFaculty().getName().equals("ФКН"));
    }

    @Test
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

        assert(userToUpdate.getFullName().equals("Петров Пётр Петрович"));
        assert(userToUpdate.getEmail().equals("petraf@mail.ru"));
        assert(userToUpdate.getUsername().equals("Petro"));
        assert(userToUpdate.getFaculty().getName().equals("ПММ"));

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

        assert(userToUpdate.getFullName().equals("Семёнов Артём Валерьевич"));
        assert(userToUpdate.getEmail().equals("semen@mail.ru"));
        assert(userToUpdate.getUsername().equals("semen"));
        assert(userToUpdate.getFaculty().getName().equals("ФКН"));
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