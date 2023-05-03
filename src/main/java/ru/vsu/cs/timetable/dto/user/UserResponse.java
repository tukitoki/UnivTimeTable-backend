package ru.vsu.cs.timetable.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
public class UserResponse {

    private Long id;
    private String role;
    private String fullName;
    private String city;
    private Long universityId;
    private Long facultyId;
    private Integer group;
}
