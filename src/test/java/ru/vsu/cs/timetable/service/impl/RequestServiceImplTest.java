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
import ru.vsu.cs.timetable.dto.group.GroupResponse;
import ru.vsu.cs.timetable.dto.univ_class.ClassDto;
import ru.vsu.cs.timetable.dto.univ_requests.MoveClassRequest;
import ru.vsu.cs.timetable.dto.univ_requests.RequestDto;
import ru.vsu.cs.timetable.dto.univ_requests.SendRequest;
import ru.vsu.cs.timetable.dto.user.UserDto;
import ru.vsu.cs.timetable.entity.*;
import ru.vsu.cs.timetable.entity.Class;
import ru.vsu.cs.timetable.entity.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.entity.enums.TypeClass;
import ru.vsu.cs.timetable.entity.enums.UserRole;
import ru.vsu.cs.timetable.entity.enums.WeekType;
import ru.vsu.cs.timetable.mapper.ClassMapper;
import ru.vsu.cs.timetable.mapper.RequestMapper;
import ru.vsu.cs.timetable.repository.ClassRepository;
import ru.vsu.cs.timetable.repository.EquipmentRepository;
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
    private UserDto lecturerDto;
    private SendRequest sendRequest;
    private RequestDto requestDto;
    private GroupResponse groupResponse;
    private User lecturer;
    private User headman;
    private Group group;
    private Request request;
    private TypeClass typeClass;
    private List<ImpossibleTime> impossibleTimes;
    private ImpossibleTime impossibleTime;
    private Set<Equipment> equipment;
    private List<String> equipmentList;
    private Faculty facultyFKN;
    private University universityVSU;
    private Map<DayOfWeekEnum, List<LocalTime>> impossibleTimeMap;
    private MoveClassRequest moveClassRequest;
    private ClassDto initClassDto;
    private ClassDto classToMoveDto;
    private Class initClass;
    private Class classToMove;
    private LocalTime timeFrom;
    private Set<Group> groupSet;
    private Audience audienceInit;
    private Audience audienceToMove;
    private List<Class> classList;

    @BeforeEach
    void setUp() {
        classList = new ArrayList<>();
        audienceToMove = new Audience();
        audienceInit = new Audience();
        groupSet = new HashSet<>();
        initClass = new Class();
        classToMove = new Class();
        classToMoveDto = new ClassDto();
        initClassDto = new ClassDto();
        moveClassRequest = new MoveClassRequest();
        impossibleTimeMap = new HashMap<>();
        universityVSU = new University();
        facultyFKN = new Faculty();
        facultyFKN = new Faculty();
        equipmentList = new ArrayList<>();
        equipment = new HashSet<>();
        impossibleTime = new ImpossibleTime();
        impossibleTimes= new ArrayList<>();
        typeClass = TypeClass.LECTURE;
        request = new Request();
        group = new Group();
        lecturer = new User();
        headman = new User();
        groupResponse = new GroupResponse();
        sendRequest = new SendRequest();
        lecturerDto = new UserDto(1L, UserRole.valueOf("LECTURER"), "Иванов Иван Иванович", "ivan",
                "ivan@mail.ru", "Воронеж", "password", null, null, null);

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

//        classList.set(0, initClass);

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
//        when(mailService.sendClassChangeMail(lecturer, initClass, classToMove, headman.getEmail())).
//                thenReturn();


        requestServiceImpl.moveClass(moveClassRequest, "ivan");
    }

}