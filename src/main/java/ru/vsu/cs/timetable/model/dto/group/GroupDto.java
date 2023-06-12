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

    @Schema(description = "Id группы", example = "1")
    private Long id;
    @NotNull
    @Positive
    @Schema(description = "Номер группы", example = "1")
    private Integer groupNumber;
    @NotNull
    @Positive
    @Schema(description = "Номер курса", example = "1")
    private Integer courseNumber;
    @NotNull
    @Positive
    @Schema(description = "Количество студентов в группе", example = "30")
    private Integer studentsAmount;
    @Schema(description = "Староста группы")
    private UserResponse headman;
}
