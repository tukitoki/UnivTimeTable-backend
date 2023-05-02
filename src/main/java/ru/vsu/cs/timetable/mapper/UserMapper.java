package ru.vsu.cs.timetable.mapper;

import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.dto.user.CreateUserRequest;
import ru.vsu.cs.timetable.model.Group;
import ru.vsu.cs.timetable.model.University;
import ru.vsu.cs.timetable.model.User;

@Component
public class UserMapper {

    public User toEntity(CreateUserRequest userDto, University university,
                          Group group, String password) {
        return User.builder()
                .name(userDto.getFullName())
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .password(password)
                .group(group)
                .university(university)
                .build();
    }
}
