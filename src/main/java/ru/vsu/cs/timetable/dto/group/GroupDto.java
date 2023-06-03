package ru.vsu.cs.timetable.dto.group;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.dto.user.UserResponse;

@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
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
    @Positive
    private UserResponse headman;
}
