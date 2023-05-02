package ru.vsu.cs.timetable.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CreateUserRequest {

    private String role;
    private String fullName;
    private String username;
    private String email;
    private String password;
    private Long universityId;
    private Long facultyId;
    private Integer group;
}
