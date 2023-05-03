package ru.vsu.cs.timetable.mapper;

import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.dto.group.GroupDto;
import ru.vsu.cs.timetable.model.Group;

@Component
public class GroupMapper {

    public GroupDto toDto(Group group) {
        return GroupDto.builder()
                .id(group.getId())
                .groupNumber(group.getGroupNumber())
                .courseNumber(group.getCourseNumber())
                .headmanId(group.getHeadmanId())
                .build();
    }
}
