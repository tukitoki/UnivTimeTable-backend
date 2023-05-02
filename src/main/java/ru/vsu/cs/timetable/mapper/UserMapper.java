package ru.vsu.cs.timetable.mapper;

import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.dto.user.CreateUserRequest;
import ru.vsu.cs.timetable.model.Faculty;
import ru.vsu.cs.timetable.model.Group;
import ru.vsu.cs.timetable.model.University;
import ru.vsu.cs.timetable.model.User;
import ru.vsu.cs.timetable.model.enums.UserRole;

@Component
public class UserMapper {

    public User toEntity(CreateUserRequest userDto, University university,
                         Group group, Faculty faculty, String password) {
        return User.builder()
                .fullName(userDto.getFullName())
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .city(userDto.getCity())
                .password(password)
                .role(UserRole.valueOf(userDto.getRole()))
                .group(group)
                .faculty(faculty)
                .university(university)
                .build();
    }
}
