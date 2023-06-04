package ru.vsu.cs.timetable.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.model.dto.group.GroupDto;
import ru.vsu.cs.timetable.model.entity.Group;
import ru.vsu.cs.timetable.model.entity.User;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class GroupMapper {

    private final UserMapper userMapper;

    public GroupDto toDto(Group group) {
        User headman = group.getUsers().stream()
                .filter(user -> user.getId().equals(group.getHeadmanId()))
                .findFirst()
                .orElse(null);

        return GroupDto.builder()
                .id(group.getId())
                .groupNumber(group.getGroupNumber())
                .courseNumber(group.getCourseNumber())
                .studentsAmount(group.getStudentsAmount())
                .headman(headman == null
                        ? null
                        : userMapper.toResponse(headman))
                .build();
    }

    public Group toEntity(GroupDto groupDto) {
        return Group.builder()
                .id(groupDto.getId())
                .courseNumber(groupDto.getCourseNumber())
                .studentsAmount(groupDto.getStudentsAmount())
                .groupNumber(groupDto.getGroupNumber())
                .headmanId(groupDto.getHeadman().getId())
                .build();
    }

    public Group toEntity(GroupDto groupDto, User user) {
        List<User> users = new ArrayList<>(List.of(user));

        return Group.builder()
                .id(groupDto.getId())
                .courseNumber(groupDto.getCourseNumber())
                .groupNumber(groupDto.getGroupNumber())
                .studentsAmount(groupDto.getStudentsAmount())
                .headmanId(groupDto.getHeadman().getId())
                .users(users)
                .build();
    }
}
