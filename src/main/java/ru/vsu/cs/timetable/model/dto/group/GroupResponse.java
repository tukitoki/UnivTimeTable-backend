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

    private Long id;
    private Integer groupNumber;
    private Integer courseNumber;
}
