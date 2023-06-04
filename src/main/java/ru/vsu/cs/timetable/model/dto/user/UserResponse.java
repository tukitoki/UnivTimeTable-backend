package ru.vsu.cs.timetable.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.entity.enums.UserRole;

@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
public class UserResponse {

    private Long id;
    private UserRole role;
    private String fullName;
    private String city;
    private String univName;
    private String facultyName;
    private Integer group;
}
