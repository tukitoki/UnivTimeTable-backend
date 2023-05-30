package ru.vsu.cs.timetable.mapper;

import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.dto.univ_requests.SendRequest;
import ru.vsu.cs.timetable.entity.*;

import java.util.List;
import java.util.Set;

@Component
public class RequestMapper {

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
}
