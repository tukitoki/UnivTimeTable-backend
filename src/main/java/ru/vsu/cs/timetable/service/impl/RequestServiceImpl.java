package ru.vsu.cs.timetable.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.timetable.dto.group.GroupResponse;
import ru.vsu.cs.timetable.dto.univ_class.MoveClassDto;
import ru.vsu.cs.timetable.dto.univ_requests.MoveClassRequest;
import ru.vsu.cs.timetable.dto.univ_requests.MoveClassResponse;
import ru.vsu.cs.timetable.dto.univ_requests.SendRequestDto;
import ru.vsu.cs.timetable.dto.univ_requests.ShowSendRequestDto;
import ru.vsu.cs.timetable.entity.Equipment;
import ru.vsu.cs.timetable.entity.ImpossibleTime;
import ru.vsu.cs.timetable.entity.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.entity.enums.TypeClass;
import ru.vsu.cs.timetable.exception.EquipmentException;
import ru.vsu.cs.timetable.mapper.RequestMapper;
import ru.vsu.cs.timetable.repository.ClassRepository;
import ru.vsu.cs.timetable.repository.EquipmentRepository;
import ru.vsu.cs.timetable.repository.RequestRepository;
import ru.vsu.cs.timetable.service.GroupService;
import ru.vsu.cs.timetable.service.RequestService;
import ru.vsu.cs.timetable.service.UserService;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RequestServiceImpl implements RequestService {

    private final UserService userService;
    private final GroupService groupService;
    private final RequestMapper requestMapper;
    private final RequestRepository requestRepository;
    private final EquipmentRepository equipmentRepository;
    private final ClassRepository classRepository;

    @Override
    public void sendRequest(SendRequestDto sendRequestDto, String username) {
        var lecturer = userService.getUserByUsername(username);
        var group = groupService.findGroupById(sendRequestDto.getGroupResponse().getId());

        List<ImpossibleTime> impossibleTimes = new ArrayList<>();
        sendRequestDto.getImpossibleTime().forEach((day, times) -> times.forEach(time -> {
            impossibleTimes.add(ImpossibleTime.builder()
                    .dayOfWeek(DayOfWeekEnum.fromName(day))
                    .timeFrom(LocalTime.parse(time))
                    .build());
        }));
        var requestEquipment = sendRequestDto.getEquipments()
                .stream()
                .map(equipment -> equipmentRepository.findByDisplayNameIgnoreCase(equipment)
                        .orElseThrow(EquipmentException.CODE.EQUIPMENT_NOT_EXIST::get))
                .collect(Collectors.toSet());

        var request = requestMapper.toEntity(sendRequestDto, lecturer, group, impossibleTimes, requestEquipment);

        requestRepository.save(request);
    }

    @Override
    public ShowSendRequestDto showSendRequest(String username) {
        var lecturer = userService.getUserByUsername(username);

        var typeClasses = Arrays.stream(TypeClass.values())
                .map(TypeClass::toString)
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

        return ShowSendRequestDto.builder()
                .typesOfClass(typeClasses)
                .equipments(equipments)
                .groupsOfCourse(groups)
                .build();
    }

    @Override
    public void moveClass(MoveClassRequest moveClassRequest, String username) {

    }

    @Override
    public MoveClassResponse showMoveClass(String username) {
        var lecturer = userService.getUserByUsername(username);

        List<MoveClassDto> classes = new LinkedList<>();

        for (var univClass : classRepository.findAllByLecturer(lecturer)) {
            MoveClassDto classDto = classes.stream()
                    .filter(existClass -> existClass.getSubject().equals(univClass.getSubjectName()))
                    .findFirst()
                    .orElse(null);
            if (classDto != null) {
                classDto.getSubjectTimes().put(univClass.getDayOfWeek().toString(),
                        new ArrayList<>(List.of(univClass.getStartTime().toString())));
                continue;
            }

            Map<String, List<String>> subjectTimes = new HashMap<>();
            subjectTimes.put(univClass.getDayOfWeek().toString(),
                    new ArrayList<>(List.of(univClass.getStartTime().toString())));

            MoveClassDto moveClassDto = MoveClassDto.builder()
                    .subject(univClass.getSubjectName())
                    .subjectTimes(subjectTimes)
                    .build();
            classes.add(moveClassDto);
        }

        return null;
    }
}
