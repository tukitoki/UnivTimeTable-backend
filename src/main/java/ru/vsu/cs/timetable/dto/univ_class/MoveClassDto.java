package ru.vsu.cs.timetable.dto.univ_class;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
public class MoveClassDto {

    private Set<Integer> groups;
    private List<ClassDto> groupClasses;
}
