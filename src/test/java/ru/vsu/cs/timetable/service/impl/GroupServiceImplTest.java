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
import ru.vsu.cs.timetable.dto.group.GroupDto;
import ru.vsu.cs.timetable.dto.user.UserResponse;
import ru.vsu.cs.timetable.entity.Class;
import ru.vsu.cs.timetable.entity.Faculty;
import ru.vsu.cs.timetable.entity.Group;
import ru.vsu.cs.timetable.entity.University;
import ru.vsu.cs.timetable.entity.User;
import ru.vsu.cs.timetable.entity.enums.UserRole;
import ru.vsu.cs.timetable.mapper.GroupMapper;
import ru.vsu.cs.timetable.repository.GroupRepository;
import ru.vsu.cs.timetable.repository.UserRepository;
import ru.vsu.cs.timetable.service.FacultyService;
import ru.vsu.cs.timetable.service.GroupService;
import ru.vsu.cs.timetable.service.UniversityService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class GroupServiceImplTest {

    @Mock
    private GroupRepository groupRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GroupMapper groupMapper;
    @InjectMocks
    private GroupServiceImpl groupServiceImpl;
    @Mock
    private UniversityService universityService;
    @Mock
    private GroupService groupService;
    @Mock
    private FacultyService facultyService;

    private UserRole userRole;
    private GroupDto groupDto;
    private Group group;
    private User user;
    private Faculty facultyFKN;
    private Faculty facultyPMM;

    @BeforeEach
    void setUp() {
        userRole = UserRole.HEADMAN;
        UserResponse userResponse = new UserResponse(1L, userRole, "Иванов Иван Иванович", "Воронеж", "ВГУ",
                "ФКН", 5);
        groupDto = new GroupDto(1L, 5, 3, 30, userResponse);
        group = new Group();
        user = new User();
        University universityVSU = new University();
        facultyFKN = new Faculty();
        facultyPMM = new Faculty();

        group.setId(1L);
        group.setGroupNumber(5);
        group.setStudentsAmount(30);
        group.setFaculty(facultyFKN);
        group.setCourseNumber(3);

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
    @DisplayName("Should successfully find group DTO by id")
    void getGroupById() {
        when(groupRepository.findById(1L))
                .thenReturn(Optional.of(group));
        when(groupMapper.toDto(group)).
                thenReturn(groupDto);

        GroupDto groupDto1 = groupServiceImpl.getGroupById(1L);

        assertThat(groupDto1.getId()).isNotNull();
        assertEquals(groupDto1.getId(), 1L);
        assertEquals(groupDto1, groupDto);
    }

    @Test
    @DisplayName("Should successfully find group by id")
    void findGroupById() {
        when(groupRepository.findById(1L))
                .thenReturn(Optional.of(group));

        Group groupToCompare = groupServiceImpl.findGroupById(1L);

        assertThat(group.getGroupNumber()).isNotNull();
        assertEquals(groupToCompare.getStudentsAmount(), 30);
        assertEquals(groupToCompare.getCourseNumber(), 3);
        assertEquals(groupToCompare.getGroupNumber(), 5);
        assertEquals(groupToCompare.getFaculty(), facultyFKN);
        assertEquals(groupToCompare, group);
    }

    @Test
    @DisplayName("Should successfully create group")
    void createGroup() {
        UserResponse userResponse1 = new UserResponse(2L, userRole, "Иванов Иван Иванович", "Воронеж", "ВГУ",
                "ФКН", 5);
        GroupDto groupToCreateDto = new GroupDto(null, 5, 2, 33, userResponse1);
        //т.к. id - Serial, то сервисом всегда будет создаваться группа с id=null

        when(facultyService.findFacultyById(1L)).
                thenReturn(facultyFKN);
        when(userRepository.findById(2L)).
                thenReturn(Optional.of(user));

        Set<Class> classes = new HashSet<>();
        Group groupToCreate = new Group();
        groupToCreate.setHeadmanId(2L);
        groupToCreate.setGroupNumber(5);
        groupToCreate.setStudentsAmount(33);
        groupToCreate.setFaculty(facultyFKN);
        groupToCreate.setCourseNumber(2);
        groupToCreate.setClasses(classes);

        when(groupRepository.save(any())).thenReturn(groupToCreate);
        groupServiceImpl.createGroup(groupToCreateDto, 1L);
    }

    @Test
    @DisplayName("Should successfully delete group")
    void deleteGroup() {
        Group groupToDelete = new Group();
        groupToDelete.setId(2L);
        groupToDelete.setGroupNumber(91);
        groupToDelete.setStudentsAmount(25);
        groupToDelete.setFaculty(facultyPMM);
        groupToDelete.setCourseNumber(4);

        when(groupRepository.findById(2L))
                .thenReturn(Optional.of(groupToDelete));
        groupServiceImpl.deleteGroup(2L);
    }

    @Test
    @DisplayName("Should successfully update group")
    void updateGroup() {
        Group groupToUpdate = new Group();
        groupToUpdate.setId(3L);
        groupToUpdate.setGroupNumber(4);
        groupToUpdate.setStudentsAmount(27);
        groupToUpdate.setFaculty(facultyFKN);
        groupToUpdate.setCourseNumber(1);

        UserResponse userResponse2 = new UserResponse(3L, userRole, "Иванов Петр Алексеевич", "Воронеж", "ВГУ",
                "ФКН", 4);
        GroupDto newGroupDto = new GroupDto(3L, 4, 1, 27, userResponse2);

        Group newGroup = new Group();
        newGroup.setId(3L);
        newGroup.setGroupNumber(5);
        newGroup.setStudentsAmount(15);
        newGroup.setFaculty(facultyFKN);
        newGroup.setCourseNumber(2);
        newGroup.setHeadmanId(3L);

        User headmanOfTheGroup = new User();
        headmanOfTheGroup.setCity("Воронеж");
        headmanOfTheGroup.setEmail("petraf@mail.ru");
        headmanOfTheGroup.setFullName("Иванов Петр Алексеевич");
        headmanOfTheGroup.setId(3L);
        headmanOfTheGroup.setUsername("Petro");
        headmanOfTheGroup.setRole(UserRole.valueOf("HEADMAN"));
        headmanOfTheGroup.setPassword("password");
        headmanOfTheGroup.setFaculty(facultyFKN);
        headmanOfTheGroup.setGroup(newGroup);

        when(groupRepository.findById(3L))
                .thenReturn(Optional.of(groupToUpdate));
        when(groupMapper.toEntity(newGroupDto, headmanOfTheGroup)).
                thenReturn(newGroup);
        when(userRepository.findById(newGroupDto.getHeadman().getId())).
                thenReturn(Optional.of(headmanOfTheGroup));
        when(groupRepository.save(groupToUpdate)).
                thenReturn(groupToUpdate);

        groupServiceImpl.updateGroup(newGroupDto, 3L);

        assertEquals(groupToUpdate.getGroupNumber(), 5);
        assertEquals(groupToUpdate.getStudentsAmount(), 15);
        assertEquals(groupToUpdate.getCourseNumber(), 2);
        assertEquals(groupToUpdate.getHeadmanId(), 3L);
    }
}