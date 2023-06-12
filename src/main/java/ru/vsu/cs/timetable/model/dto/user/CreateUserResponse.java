package ru.vsu.cs.timetable.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.university.UniversityResponse;
import ru.vsu.cs.timetable.model.enums.UserRole;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@Schema(description = "Информация для создания пользователя")
public class CreateUserResponse {

    @Schema(description = "Список ролей", example = "[\"LECTURER\", \"HEADMAN\", \"ADMIN\"]")
    private List<UserRole> roles;
    @Schema(description = "Информация об университетах")
    private List<UniversityResponse> universityResponses;
}
