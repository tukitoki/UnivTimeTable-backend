package ru.vsu.cs.timetable.dto.faculty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.dto.group.GroupDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class FacultyDto {

    private Long id;
    private String name;
    private List<GroupDto> groups;
}
