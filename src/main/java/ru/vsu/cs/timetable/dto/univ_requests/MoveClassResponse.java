package ru.vsu.cs.timetable.dto.univ_requests;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.dto.univ_class.MoveClassDto;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@SuperBuilder
public class MoveClassResponse {

    private List<MoveClassDto> classes;
    private Map<Integer, List<String>> possibleTimesInAudience;
}
