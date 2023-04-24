package ru.vsu.cs.timetable.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder
public class TimetableResponse {

    private List<ClassDto> classes;
}
