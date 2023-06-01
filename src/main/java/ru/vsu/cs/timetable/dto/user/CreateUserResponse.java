package ru.vsu.cs.timetable.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.dto.university.UniversityDto;
import ru.vsu.cs.timetable.entity.enums.UserRole;

import java.util.List;

@Setter
@Getter
@SuperBuilder
public class CreateUserResponse {

    private List<UserRole> roles;
    private List<UniversityDto> universityDtos;
}
