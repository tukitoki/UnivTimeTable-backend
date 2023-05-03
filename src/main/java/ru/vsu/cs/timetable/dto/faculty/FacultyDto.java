package ru.vsu.cs.timetable.dto.faculty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.dto.group.GroupDto;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class FacultyDto {

    private Long id;
    private String name;
    private List<GroupDto> groups;
}
