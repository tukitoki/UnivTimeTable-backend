package ru.vsu.cs.timetable.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.timetable.dto.group.GroupResponse;
import ru.vsu.cs.timetable.dto.univ_class.MoveClassDto;
import ru.vsu.cs.timetable.dto.univ_requests.MoveClassRequest;
import ru.vsu.cs.timetable.dto.univ_requests.MoveClassResponse;
import ru.vsu.cs.timetable.dto.univ_requests.SendRequest;
import ru.vsu.cs.timetable.dto.univ_requests.ShowSendRequest;
import ru.vsu.cs.timetable.dto.week_time.DayTimes;
import ru.vsu.cs.timetable.entity.Class;
import ru.vsu.cs.timetable.entity.Equipment;
import ru.vsu.cs.timetable.entity.Group;
import ru.vsu.cs.timetable.entity.ImpossibleTime;
import ru.vsu.cs.timetable.entity.enums.TypeClass;
import ru.vsu.cs.timetable.exception.AudienceException;
import ru.vsu.cs.timetable.exception.ClassException;
import ru.vsu.cs.timetable.exception.EquipmentException;
import ru.vsu.cs.timetable.mapper.ClassMapper;
import ru.vsu.cs.timetable.mapper.RequestMapper;
import ru.vsu.cs.timetable.repository.ClassRepository;
import ru.vsu.cs.timetable.repository.EquipmentRepository;
import ru.vsu.cs.timetable.repository.RequestRepository;
import ru.vsu.cs.timetable.service.AudienceService;
import ru.vsu.cs.timetable.service.GroupService;
import ru.vsu.cs.timetable.service.RequestService;
import ru.vsu.cs.timetable.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class RequestServiceImpl implements RequestService {

    private final UserService userService;
    private final GroupService groupService;
    private final AudienceService audienceService;
    private final RequestMapper requestMapper;
    private final ClassMapper classMapper;
    private final RequestRepository requestRepository;
    private final EquipmentRepository equipmentRepository;
    private final ClassRepository classRepository;

    @Override
    public void sendRequest(SendRequest sendRequest, String username) {
        var lecturer = userService.getUserByUsername(username);
        var group = groupService.findGroupById(sendRequest.getGroupResponse().getId());

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

        requestRepository.save(request);
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

        var initClass = classRepository.findClassToMove(initClassDto.getSubjectName(), initClassDto.getStartTime(),
                        initClassDto.getAudience(), initClassDto.getDayOfWeek(),
                        initClassDto.getTypeOfClass(), initClassDto.getWeekType(), lecturer)
                .orElseThrow(ClassException.CODE.INCORRECT_CLASS_TO_MOVE::get);
        var classToMove = classMapper.toEntity(classDtoToMove);

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

        classRepository.save(initClass);
    }

    @Override
    @Transactional(readOnly = true)
    public MoveClassResponse showMoveClass(String username) {
        var lecturer = userService.getUserByUsername(username);

        Map<Integer, List<MoveClassDto>> coursesClasses = new TreeMap<>();

        for (var univClass : classRepository.findAllByLecturer(lecturer)) {
            var course = univClass.getGroups().stream()
                    .map(Group::getCourseNumber)
                    .findFirst()
                    .orElseThrow(ClassException.CODE.WRONG_CLASS_FOUND::get);

            if (!coursesClasses.containsKey(course)) {
                coursesClasses.put(course, new LinkedList<>());
            }
            var courseClasses = coursesClasses.get(course);
            var allGroups = univClass.getGroups().stream()
                    .map(Group::getGroupNumber)
                    .collect(Collectors.toSet());

            MoveClassDto currGroupClasses = null;
            for (var courseClass : courseClasses) {
                if (courseClass.getGroups().equals(allGroups)) {
                    currGroupClasses = courseClass;
                    break;
                }
            }

            if (currGroupClasses == null) {
                currGroupClasses = MoveClassDto.builder()
                        .groups(allGroups)
                        .groupClasses(new ArrayList<>(List.of(classMapper.toDto(univClass))))
                        .build();
                courseClasses.add(currGroupClasses);
            } else {
                currGroupClasses.getGroupClasses().add(classMapper.toDto(univClass));
            }
        }

        var audiencesFreeTime = audienceService.getFreeAudienceByFaculty(lecturer.getFaculty());
        Map<Integer, List<DayTimes>> possibleTimesInAudience = new HashMap<>();

        audiencesFreeTime.forEach((audience, freeTimes) -> {
            possibleTimesInAudience.put(audience.getAudienceNumber(), freeTimes);
        });

        return MoveClassResponse.builder()
                .coursesClasses(coursesClasses)
                .possibleTimesInAudience(possibleTimesInAudience)
                .build();
    }

    private void copyClassProperties(Class from, Class to) {
        BeanUtils.copyProperties(from, to, "id", "subjectName", "typeClass", "lecturer",
                "timetable", "groups");
    }
}
