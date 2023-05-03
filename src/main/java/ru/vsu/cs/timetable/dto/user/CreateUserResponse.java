package ru.vsu.cs.timetable.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.dto.university.UniversityDto;

import java.util.List;

@Setter
@Getter
@SuperBuilder
public class CreateUserResponse {

    private List<String> roles;
    private List<UniversityDto> universityDtos;
}
