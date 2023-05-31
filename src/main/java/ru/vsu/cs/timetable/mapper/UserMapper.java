package ru.vsu.cs.timetable.mapper;

import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.dto.user.UserDto;
import ru.vsu.cs.timetable.dto.user.UserResponse;
import ru.vsu.cs.timetable.entity.Faculty;
import ru.vsu.cs.timetable.entity.Group;
import ru.vsu.cs.timetable.entity.University;
import ru.vsu.cs.timetable.entity.User;

@Component
public class UserMapper {

    public User toEntity(UserDto userDto, University university,
                         Group group, Faculty faculty, String password) {
        return User.builder()
                .fullName(userDto.getFullName())
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .city(userDto.getCity())
                .password(password)
                .role(userDto.getRole())
                .group(group)
                .faculty(faculty)
                .university(university)
                .build();
    }

    public UserDto toDto(User user) {
        Long univId = user.getUniversity() == null
                ? null
                : user.getUniversity().getId();
        Long facultyId = user.getFaculty() == null
                ? null
                : user.getFaculty().getId();
        Long groupId = user.getGroup() == null
                ? null
                : user.getGroup().getId();

        return UserDto.builder()
                .id(user.getId())
                .role(user.getRole())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .email(user.getEmail())
                .city(user.getCity())
                .universityId(univId)
                .facultyId(facultyId)
                .groupId(groupId)
                .build();
    }

    public UserResponse toResponse(User user) {
        Long univId = user.getUniversity() == null
                ? null
                : user.getUniversity().getId();
        Long facultyId = user.getFaculty() == null
                ? null
                : user.getFaculty().getId();
        Integer groupId = user.getGroup() == null
                ? null
                : user.getGroup().getGroupNumber();
        return UserResponse.builder()
                .id(user.getId())
                .role(user.getRole().name())
                .fullName(user.getFullName())
                .city(user.getCity())
                .universityId(univId)
                .facultyId(facultyId)
                .group(groupId)
                .build();
    }
}
