package ru.vsu.cs.timetable.model.dto.group;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@Schema(description = "Группы с списком курсов")
public class GroupViewDto {

    @Schema(description = "Группы факультета")
    private List<GroupDto> groups;
    @Schema(description = "Курсы факультета", example = "[\"1\", \"2\", \"3\"]")
    private List<Integer> courses;
}
