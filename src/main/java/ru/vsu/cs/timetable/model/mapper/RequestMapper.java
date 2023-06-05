package ru.vsu.cs.timetable.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.model.dto.univ_requests.RequestDto;
import ru.vsu.cs.timetable.model.dto.univ_requests.SendRequest;
import ru.vsu.cs.timetable.model.entity.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class RequestMapper {

    private final UserMapper userMapper;

    public Request toEntity(SendRequest requestDto, User lecturer, Group group,
                            List<ImpossibleTime> impossibleTimes, Set<Equipment> equipment) {
        var request = Request.builder()
                .subjectName(requestDto.getSubjectName())
                .subjectHourPerWeek(requestDto.getSubjectHourPerWeek())
                .typeClass(requestDto.getTypeClass())
                .lecturer(lecturer)
                .group(group)
                .impossibleTimes(impossibleTimes)
                .requiredEquipments(equipment)
                .build();

        impossibleTimes.forEach(impossibleTime -> impossibleTime.setRequest(request));

        return request;
    }

    public RequestDto toDto(Request request) {
        return RequestDto.builder()
                .subjectName(request.getSubjectName())
                .typeClass(request.getTypeClass())
                .subjectHourPerWeek(request.getSubjectHourPerWeek())
                .userDto(userMapper.toDto(request.getLecturer()))
                .build();
    }
}
