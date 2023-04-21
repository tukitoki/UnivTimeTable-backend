package ru.vsu.cs.timetable.dto.user;

import ru.vsu.cs.timetable.dto.university.UniversityDto;

import java.util.List;

public class CreateUserResponse {

    List<String> roles;
    List<UniversityDto> universityDtos;
}
