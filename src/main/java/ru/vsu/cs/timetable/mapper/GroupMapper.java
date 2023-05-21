package ru.vsu.cs.timetable.mapper;

import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.dto.group.GroupDto;
import ru.vsu.cs.timetable.model.Group;
import ru.vsu.cs.timetable.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupMapper {

    public GroupDto toDto(Group group) {
        return GroupDto.builder()
                .id(group.getId())
                .groupNumber(group.getGroupNumber())
                .courseNumber(group.getCourseNumber())
                .studentsAmount(group.getStudentsAmount())
                .headmanId(group.getHeadmanId())
                .build();
    }

    public Group toEntity(GroupDto groupDto) {
        return Group.builder()
                .id(groupDto.getId())
                .courseNumber(groupDto.getCourseNumber())
                .studentsAmount(groupDto.getStudentsAmount())
                .groupNumber(groupDto.getGroupNumber())
                .headmanId(groupDto.getHeadmanId())
                .build();
    }

    public Group toEntity(GroupDto groupDto, User user) {
        List<User> users = new ArrayList<>(List.of(user));

        return Group.builder()
                .id(groupDto.getId())
                .courseNumber(groupDto.getCourseNumber())
                .groupNumber(groupDto.getGroupNumber())
                .studentsAmount(groupDto.getStudentsAmount())
                .headmanId(groupDto.getHeadmanId())
                .users(users)
                .build();
    }
}
