package ru.vsu.cs.timetable.dto.univ_requests;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@SuperBuilder
public class ShowSendRequestDto {

    private List<String> typesOfClass;
    private List<String> equipments;
    private Map<Integer, List<Integer>> groupsOfCourse;
}
