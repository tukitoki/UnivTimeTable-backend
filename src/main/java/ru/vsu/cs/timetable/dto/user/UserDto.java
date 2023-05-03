package ru.vsu.cs.timetable.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
public class UserDto {

    private Long id;
    private String role;
    private String fullName;
    private String username;
    private String email;
    private String city;
    private String password;
    private Long universityId;
    private Long facultyId;
    private Long groupId;
}
