package ru.vsu.cs.timetable.dto.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
public class GroupDto {

    private Long id;
    private Integer groupNumber;
    private Integer courseNumber;
    private Integer studentsAmount;
    private Long headmanId;
}
