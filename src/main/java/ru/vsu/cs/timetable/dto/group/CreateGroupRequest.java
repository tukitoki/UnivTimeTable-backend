package ru.vsu.cs.timetable.dto.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CreateGroupRequest {

    private Integer groupNumber;
    private Integer courseNumber;
    private Long headmanId;
}
