package ru.vsu.cs.timetable.model.dto.group;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class GroupResponse {

    private Long id;
    private Integer groupNumber;
    private Integer courseNumber;
}
