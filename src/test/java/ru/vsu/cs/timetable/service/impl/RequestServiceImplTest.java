package ru.vsu.cs.timetable.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.cs.timetable.dto.group.GroupResponse;
import ru.vsu.cs.timetable.dto.univ_requests.RequestDto;
import ru.vsu.cs.timetable.dto.univ_requests.SendRequest;
import ru.vsu.cs.timetable.dto.user.UserDto;
import ru.vsu.cs.timetable.entity.*;
import ru.vsu.cs.timetable.entity.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.entity.enums.TypeClass;
import ru.vsu.cs.timetable.entity.enums.UserRole;
import ru.vsu.cs.timetable.mapper.ClassMapper;
import ru.vsu.cs.timetable.mapper.RequestMapper;
import ru.vsu.cs.timetable.repository.ClassRepository;
import ru.vsu.cs.timetable.repository.EquipmentRepository;
import ru.vsu.cs.timetable.repository.GroupRepository;
import ru.vsu.cs.timetable.repository.RequestRepository;
import ru.vsu.cs.timetable.service.AudienceService;
import ru.vsu.cs.timetable.service.GroupService;
import ru.vsu.cs.timetable.service.MailService;
import ru.vsu.cs.timetable.service.UserService;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class RequestServiceImplTest {
    @Mock
    private UserService userService;
    @Mock
    private GroupService groupService;
    @Mock
    private AudienceService audienceService;
    @Mock
    private MailService mailService;
    @Mock
    private RequestMapper requestMapper;
    @Mock
    private ClassMapper classMapper;
    @Mock
    private RequestRepository requestRepository;
    @Mock
    private EquipmentRepository equipmentRepository;
    @Mock
    private ClassRepository classRepository;
    @InjectMocks
    private RequestServiceImpl requestServiceImpl;
    private final UserDto lecturerDto = new UserDto(1L, UserRole.valueOf("LECTURER"), "Иванов Иван Иванович", "ivan",
            "ivan@mail.ru", "Воронеж", "password", null, null, null);
    private SendRequest sendRequest = new SendRequest();
    private RequestDto requestDto;
    private GroupResponse groupResponse = new GroupResponse();
    private User lecturer = new User();
    private Group group = new Group();
    private Request request = new Request();
    private TypeClass typeClass = TypeClass.LECTURE;
    private List<ImpossibleTime> impossibleTimes= new ArrayList<>();
    private ImpossibleTime impossibleTime = new ImpossibleTime();
    private Set<Equipment> equipment = new HashSet<>();
    private List<String> equipmentList = new ArrayList<>();
    private Faculty facultyFKN = new Faculty();
    private University universityVSU = new University();
    private Map<DayOfWeekEnum, List<LocalTime>> impossibleTimeMap = new HashMap<>();
    private LocalTime timeFrom;

    @BeforeEach
    void setUp() {
        lecturer.setCity("Воронеж");
        lecturer.setEmail("ivan@mail.ru");
        lecturer.setFullName("Иванов Иван Иванович");
        lecturer.setId(1L);
        lecturer.setUsername("ivan");
        lecturer.setRole(UserRole.valueOf("LECTURER"));
        lecturer.setPassword("password");
        lecturer.setGroup(group);

        requestDto = RequestDto.builder()
                        .subjectName("ТФКП")
                        .subjectHourPerWeek(BigDecimal.valueOf(2))
                        .typeClass(typeClass)
                        .userDto(lecturerDto)
                        .build();

        requestDto.setSubjectName("ТФКП");
        requestDto.setSubjectHourPerWeek(BigDecimal.valueOf(2));
        requestDto.setTypeClass(typeClass);
        requestDto.setUserDto(lecturerDto);

        groupResponse.setId(1L);
        groupResponse.setGroupNumber(5);
        groupResponse.setCourseNumber(3);

        sendRequest.setEquipments(equipmentList);
        sendRequest.setGroupResponse(groupResponse);
        sendRequest.setSubjectHourPerWeek(BigDecimal.valueOf(2));
        sendRequest.setSubjectName("ТФКП");
        sendRequest.setTypeClass(typeClass);
        sendRequest.setImpossibleTime(impossibleTimeMap);

        universityVSU.setId(1L);
        universityVSU.setCity("Воронеж");
        universityVSU.setName("ВГУ");

        facultyFKN.setId(1L);
        facultyFKN.setName("ФКН");
        facultyFKN.setUniversity(universityVSU);

        group.setId(1L);
        group.setGroupNumber(5);
        group.setStudentsAmount(30);
        group.setCourseNumber(3);
        group.setHeadmanId(1L);
        group.setFaculty(facultyFKN);

        request.setId(1L);
        request.setGroup(group);
        request.setImpossibleTimes(impossibleTimes);
        request.setLecturer(lecturer);
        request.setRequiredEquipments(equipment);
        request.setSubjectHourPerWeek(BigDecimal.valueOf(2));
        request.setSubjectName("ТФКП");
        request.setTypeClass(typeClass);
    }

    @Test
    void sendRequest() {
        when(userService.getUserByUsername("ivan")).
                thenReturn(lecturer);
        when(groupService.findGroupById(1L)).
                thenReturn(group);
        when(requestMapper.toEntity(sendRequest, lecturer, group, impossibleTimes, equipment)).
                thenReturn(request);
        when(requestRepository.save(any())).
                thenReturn(request);

        requestServiceImpl.sendRequest(sendRequest, "ivan");
    }

    @Test
    void moveClass() {
    }

}