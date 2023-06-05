package ru.vsu.cs.timetable.model.dto.group;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.user.UserResponse;

@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
@Schema(description = "Полная информация о группе")
public class GroupDto {

    private Long id;
    @NotNull
    @Positive
    private Integer groupNumber;
    @NotNull
    @Positive
    private Integer courseNumber;
    @NotNull
    @Positive
    private Integer studentsAmount;
    private UserResponse headman;
}
