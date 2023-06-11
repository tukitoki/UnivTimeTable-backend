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
import ru.vsu.cs.timetable.logic.service.impl.RequestServiceImpl;
import ru.vsu.cs.timetable.model.dto.group.GroupResponse;
import ru.vsu.cs.timetable.model.dto.univ_class.ClassDto;
import ru.vsu.cs.timetable.model.dto.univ_requests.MoveClassRequest;
import ru.vsu.cs.timetable.model.dto.univ_requests.RequestDto;
import ru.vsu.cs.timetable.model.dto.univ_requests.SendRequest;
import ru.vsu.cs.timetable.model.dto.user.UserDto;
import ru.vsu.cs.timetable.model.entity.*;
import ru.vsu.cs.timetable.model.entity.Class;
import ru.vsu.cs.timetable.model.entity.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.model.entity.enums.TypeClass;
import ru.vsu.cs.timetable.model.entity.enums.UserRole;
import ru.vsu.cs.timetable.model.entity.enums.WeekType;
import ru.vsu.cs.timetable.model.mapper.ClassMapper;
import ru.vsu.cs.timetable.model.mapper.RequestMapper;
import ru.vsu.cs.timetable.repository.ClassRepository;
import ru.vsu.cs.timetable.repository.EquipmentRepository;
import ru.vsu.cs.timetable.repository.RequestRepository;
import ru.vsu.cs.timetable.logic.service.AudienceService;
import ru.vsu.cs.timetable.logic.service.GroupService;
import ru.vsu.cs.timetable.logic.service.MailService;
import ru.vsu.cs.timetable.logic.service.UserService;

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
    private SendRequest sendRequest;
    private User lecturer;
    private User headman;
    private Group group;
    private Request request;
    private List<ImpossibleTime> impossibleTimes;
    private Set<Equipment> equipment;
    private Faculty facultyFKN;
    private MoveClassRequest moveClassRequest;
    private ClassDto initClassDto;
    private ClassDto classToMoveDto;
    private Class initClass;
    private Class classToMove;
    private LocalTime timeFrom;
    private Audience audienceToMove;

    @BeforeEach
    void setUp() {
        List<Class> classList = new ArrayList<>();
        GroupResponse groupResponse = new GroupResponse();
        Audience audienceInit = new Audience();
        Set<Group> groupSet = new HashSet<>();
        Map<DayOfWeekEnum, List<LocalTime>> impossibleTimeMap = new HashMap<>();
        List<String> equipmentList = new ArrayList<>();
        University universityVSU = new University();
        ImpossibleTime impossibleTime = new ImpossibleTime();
        TypeClass typeClass = TypeClass.LECTURE;
        UserDto lecturerDto = new UserDto(1L, UserRole.valueOf("LECTURER"), "Иванов Иван Иванович", "ivan",
                "ivan@mail.ru", "Воронеж", "password", null, null, null);

        audienceToMove = new Audience();
        initClass = new Class();
        classToMove = new Class();
        classToMoveDto = new ClassDto();
        initClassDto = new ClassDto();
        moveClassRequest = new MoveClassRequest();
        facultyFKN = new Faculty();
        facultyFKN = new Faculty();
        equipment = new HashSet<>();
        impossibleTimes = new ArrayList<>();
        request = new Request();
        group = new Group();
        lecturer = new User();
        headman = new User();
        sendRequest = new SendRequest();


        lecturer.setCity("Воронеж");
        lecturer.setEmail("ivan@mail.ru");
        lecturer.setFullName("Иванов Иван Иванович");
        lecturer.setId(1L);
        lecturer.setUsername("ivan");
        lecturer.setRole(UserRole.LECTURER);
        lecturer.setPassword("password");
        lecturer.setGroup(group);
        lecturer.setFaculty(facultyFKN);

        headman.setCity("Воронеж");
        headman.setEmail("petruha@mail.ru");
        headman.setFullName("Пётр Петров Петрович");
        headman.setId(2L);
        headman.setUsername("petr");
        headman.setRole(UserRole.HEADMAN);
        headman.setPassword("password");
        headman.setGroup(group);

        RequestDto requestDto = RequestDto.builder()
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
        group.setHeadmanId(2L);

        groupSet.add(group);

        request.setId(1L);
        request.setGroup(group);
        request.setImpossibleTimes(impossibleTimes);
        request.setLecturer(lecturer);
        request.setRequiredEquipments(equipment);
        request.setSubjectHourPerWeek(BigDecimal.valueOf(2));
        request.setSubjectName("ТФКП");
        request.setTypeClass(typeClass);

        initClassDto.setAudience(285);
        initClassDto.setDayOfWeek(DayOfWeekEnum.FRIDAY);
        initClassDto.setSubjectName("ТФКП");
        initClassDto.setTypeOfClass(TypeClass.LECTURE);
        initClassDto.setWeekType(WeekType.DENOMINATOR);

        classToMoveDto.setAudience(270);
        classToMoveDto.setDayOfWeek(DayOfWeekEnum.SATURDAY);
        classToMoveDto.setSubjectName("ТФКП");
        classToMoveDto.setTypeOfClass(TypeClass.LECTURE);
        classToMoveDto.setWeekType(WeekType.DENOMINATOR);

        audienceInit.setId(1L);
        audienceInit.setAudienceNumber(285);
        audienceInit.setCapacity(50L);

        audienceToMove.setId(2L);
        audienceToMove.setAudienceNumber(270);
        audienceToMove.setCapacity(50L);
        audienceToMove.setClasses(classList);

        classToMove.setAudience(audienceToMove);
        classToMove.setDayOfWeek(DayOfWeekEnum.SATURDAY);
        classToMove.setGroups(groupSet);
        classToMove.setId(1L);
        classToMove.setLecturer(lecturer);
        classToMove.setSubjectName("ТФКП");
        classToMove.setTypeClass(TypeClass.LECTURE);
        classToMove.setWeekType(WeekType.DENOMINATOR);

        initClass.setAudience(audienceInit);
        initClass.setDayOfWeek(DayOfWeekEnum.FRIDAY);
        initClass.setGroups(groupSet);
        initClass.setId(2L);
        initClass.setLecturer(lecturer);
        initClass.setSubjectName("ТФКП");
        initClass.setTypeClass(TypeClass.LECTURE);
        initClass.setWeekType(WeekType.DENOMINATOR);


        moveClassRequest.setInitClass(initClassDto);
        moveClassRequest.setClassToMove(classToMoveDto);
    }

    @Test
    @DisplayName("Should successfully send request")
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
    @DisplayName("Should successfully move class")
    void moveClass() {
        when(userService.getUserByUsername("ivan")).
                thenReturn(lecturer);
        when(classRepository.findClassToMove(initClassDto.getSubjectName(), initClassDto.getStartTime(),
                initClassDto.getAudience(), initClassDto.getDayOfWeek(), initClassDto.getTypeOfClass(),
                initClassDto.getWeekType(), lecturer)).
                thenReturn(Optional.of(initClass));
        when(classMapper.toEntity(classToMoveDto)).
                thenReturn(classToMove);
        when(audienceService.findAudienceByNumberAndFaculty(classToMoveDto.getAudience(), facultyFKN)).
                thenReturn(audienceToMove);
        when(classRepository.save(any())).
                thenReturn(initClass);
        when(userService.getUserById(2L)).
                thenReturn(headman);

        requestServiceImpl.moveClass(moveClassRequest, "ivan");
    }
}