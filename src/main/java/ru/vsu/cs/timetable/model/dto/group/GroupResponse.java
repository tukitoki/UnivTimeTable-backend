package ru.vsu.cs.timetable.model.dto.group;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Schema(description = "Ифнормация о группе (только группа и курс)")
public class GroupResponse {

    @Schema(description = "Id группы", example = "1")
    private Long id;
    @Schema(description = "Курс группы", example = "1")
    private Integer courseNumber;
    @Schema(description = "Номер группы", example = "1")
    private Integer groupNumber;
}
