package ru.vsu.cs.timetable.dto.univ_class;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
public class MoveClassDto {

    private String subject;
    private Map<String, List<String>> subjectTimes;
}
