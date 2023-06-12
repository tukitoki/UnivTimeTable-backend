package ru.vsu.cs.timetable.logic.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.timetable.exception.*;
import ru.vsu.cs.timetable.logic.service.*;
import ru.vsu.cs.timetable.model.dto.audience.AudienceToMoveResponse;
import ru.vsu.cs.timetable.model.dto.group.GroupResponse;
import ru.vsu.cs.timetable.model.dto.univ_class.ClassDto;
import ru.vsu.cs.timetable.model.dto.univ_requests.*;
import ru.vsu.cs.timetable.model.dto.week_time.DayTimes;
import ru.vsu.cs.timetable.model.entity.Class;
import ru.vsu.cs.timetable.model.entity.*;
import ru.vsu.cs.timetable.model.enums.TypeClass;
import ru.vsu.cs.timetable.model.mapper.ClassMapper;
import ru.vsu.cs.timetable.model.mapper.RequestMapper;
import ru.vsu.cs.timetable.repository.ClassRepository;
import ru.vsu.cs.timetable.repository.EquipmentRepository;
import ru.vsu.cs.timetable.repository.RequestRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class RequestServiceImpl implements RequestService {

    private final UserService userService;
    private final GroupService groupService;
    private final AudienceService audienceService;
    private final MailService mailService;
    private final RequestMapper requestMapper;
    private final ClassMapper classMapper;
    private final RequestRepository requestRepository;
    private final EquipmentRepository equipmentRepository;
    private final ClassRepository classRepository;

    @Override
    public void sendRequest(SendRequest sendRequest, String username) {
        var lecturer = userService.getUserByUsername(username);
        var group = groupService.findGroupById(sendRequest.getGroupResponse().getId());

        if (sendRequest.getSubjectHourPerWeek().remainder(new BigDecimal("0.75")).compareTo(BigDecimal.ZERO) != 0) {
            throw RequestException.CODE.WRONG_SUBJECT_HOUR_PER_WEEK.get();
        }

        List<ImpossibleTime> impossibleTimes = new ArrayList<>();
        sendRequest.getImpossibleTime().forEach((day, times) ->
                times.forEach(time -> impossibleTimes.add(ImpossibleTime.builder()
                        .dayOfWeek(day)
                        .timeFrom(time)
                        .build())));
        var requestEquipment = sendRequest.getEquipments()
                .stream()
                .map(equipment -> equipmentRepository.findByDisplayNameIgnoreCase(equipment)
                        .orElseThrow(EquipmentException.CODE.EQUIPMENT_NOT_EXIST::get))
                .collect(Collectors.toSet());

        var request = requestMapper.toEntity(sendRequest, lecturer, group, impossibleTimes, requestEquipment);

        request = requestRepository.save(request);

        log.info("lecturer: {}  was successful saved request {}", lecturer, request);
    }

    @Override
    @Transactional(readOnly = true)
    public ShowSendRequest showSendRequest(String username) {
        var lecturer = userService.getUserByUsername(username);

        var typeClasses = Arrays.stream(TypeClass.values())
                .toList();
        var equipments = equipmentRepository.findAll()
                .stream()
                .map(Equipment::getDisplayName)
                .toList();

        List<GroupResponse> groups = lecturer.getFaculty().getGroups()
                .stream()
                .map(group -> {
                    var groupResponse = GroupResponse.builder()
                            .id(group.getId())
                            .courseNumber(group.getCourseNumber())
                            .groupNumber(group.getGroupNumber())
                            .build();
                    return groupResponse;
                })
                .toList();

        return ShowSendRequest.builder()
                .typesOfClass(typeClasses)
                .equipments(equipments)
                .groupsOfCourse(groups)
                .build();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void moveClass(MoveClassRequest moveClassRequest, String username) {
        var lecturer = userService.getUserByUsername(username);

        var initClassDto = moveClassRequest.getInitClass();
        var classDtoToMove = moveClassRequest.getClassToMove();

        Class initClass = classRepository.findClassToMove(initClassDto.getSubjectName(), initClassDto.getStartTime(),
                        initClassDto.getAudience(), initClassDto.getDayOfWeek(),
                        initClassDto.getTypeOfClass(), initClassDto.getWeekType(), lecturer)
                .orElseThrow(ClassException.CODE.INCORRECT_CLASS_TO_MOVE::get);
        Class classToMove = classMapper.toEntity(classDtoToMove);

        if (ifMovedClassConflict(lecturer, initClass, classToMove)) {
            throw RequestException.CODE.MOVE_CLASS_TIME_CONFLICT.get();
        }

        var audience = audienceService.findAudienceByNumberAndFaculty(classDtoToMove.getAudience(),
                lecturer.getFaculty());
        audience.getClasses().stream()
                .filter(aClass -> aClass.getWeekType() == classToMove.getWeekType()
                        && aClass.getDayOfWeek() == classToMove.getDayOfWeek()
                        && aClass.getStartTime().equals(classToMove.getStartTime()))
                .findFirst()
                .ifPresent(aClass -> {
                    throw AudienceException.CODE.AUDIENCE_IS_BUSY_FOR_LESSON.get(aClass.toString());
                });

        classToMove.setAudience(audience);
        copyClassProperties(classToMove, initClass);

        var movedClass = classRepository.save(initClass);

        movedClass.getGroups().forEach(group -> {
            if (group.getHeadmanId() != null) {
                User headman = userService.getUserById(group.getHeadmanId());
                mailService.sendClassChangeMail(lecturer, initClass, movedClass, headman.getEmail());
            }
        });

        log.info("lecturer: {}, was successful moved class from {} to {}", lecturer, initClass, movedClass);
    }

    @Override
    @Transactional(readOnly = true)
    public MoveClassResponse showMoveClass(String username) {
        var lecturer = userService.getUserByUsername(username);

        Map<Integer, List<ClassDto>> coursesClasses = new TreeMap<>();

        for (var univClass : classRepository.findAllByLecturer(lecturer)) {
            var course = univClass.getGroups().stream()
                    .map(Group::getCourseNumber)
                    .findFirst()
                    .orElseThrow(ClassException.CODE.WRONG_CLASS_FOUND::get);

            if (!coursesClasses.containsKey(course)) {
                coursesClasses.put(course, new LinkedList<>());
            }
            var courseClasses = coursesClasses.get(course);

            courseClasses.add(classMapper.toDto(univClass));
        }

        var audiencesFreeTime = audienceService.getFreeAudiencesByFaculty(lecturer.getFaculty());
        removeConflictTime(audiencesFreeTime, lecturer);

        List<AudienceToMoveResponse> audienceToMoveResponses = new ArrayList<>();

        audiencesFreeTime.forEach((audience, freeTimes) -> {
            audienceToMoveResponses.add(AudienceToMoveResponse.builder()
                    .audienceNumber(audience.getAudienceNumber())
                    .capacity(audience.getCapacity())
                    .dayTimes(freeTimes)
                    .equipments(audience.getEquipments().stream()
                            .map(Equipment::getDisplayName)
                            .collect(Collectors.toSet()))
                    .build());
        });

        if (coursesClasses.isEmpty()) {
            throw TimetableException.CODE.TIMETABLE_WAS_NOT_MADE.get();
        }

        return MoveClassResponse.builder()
                .coursesClasses(coursesClasses)
                .audienceToMoveResponses(audienceToMoveResponses)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestDto> getAllRequests(String username) {
        var lecturer = userService.getUserByUsername(username);

        List<Request> requests = requestRepository.findAllByGroupFacultyOrderByTypeClass(lecturer.getFaculty());

        return requests.stream()
                .map(requestMapper::toDto)
                .toList();
    }

    private void copyClassProperties(Class from, Class to) {
        BeanUtils.copyProperties(from, to, "id", "subjectName", "typeClass", "lecturer",
                "timetable", "groups");
    }

    private boolean ifMovedClassConflict(User lecturer, Class initClass, Class moveClass) {
        for (var aClass : classRepository.findAllByLecturer(lecturer)) {
            if (aClass.equals(initClass)) {
                continue;
            }
            if (aClass.getDayOfWeek() == moveClass.getDayOfWeek() && aClass.getStartTime() == moveClass.getStartTime()) {
                return true;
            }
        }

        return false;
    }

    private void removeConflictTime(Map<Audience, List<DayTimes>> audiencesFreeTime, User lecturer) {
        var lecturerClasses = classRepository.findAllByLecturer(lecturer);
        audiencesFreeTime.forEach((audience, dayTimes) -> {
            dayTimes.forEach(dayTimes1 -> {
                dayTimes1.getWeekTimes().forEach((weekType, localTimes) -> {
                    lecturerClasses.stream()
                            .filter(aClass -> aClass.getWeekType() == weekType
                                    && localTimes.contains(aClass.getStartTime())
                                    && aClass.getDayOfWeek() == dayTimes1.getDayOfWeek())
                            .map(Class::getStartTime)
                            .findFirst()
                            .ifPresent(localTimes::remove);
                });
            });
        });
    }
}
