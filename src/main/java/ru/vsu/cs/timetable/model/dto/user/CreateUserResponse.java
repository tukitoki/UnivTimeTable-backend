package ru.vsu.cs.timetable.model.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.university.UniversityResponse;
import ru.vsu.cs.timetable.model.entity.enums.UserRole;

import java.util.List;

@Setter
@Getter
@SuperBuilder
public class CreateUserResponse {

    private List<UserRole> roles;
    private List<UniversityResponse> universityResponses;
}
