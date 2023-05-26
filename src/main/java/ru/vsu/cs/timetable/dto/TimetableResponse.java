package ru.vsu.cs.timetable.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.entity.Class;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@SuperBuilder
public class TimetableResponse {

    private Map<String, List<Class>> classes;
}
