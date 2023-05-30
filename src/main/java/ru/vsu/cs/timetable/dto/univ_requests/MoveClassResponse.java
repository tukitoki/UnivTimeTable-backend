package ru.vsu.cs.timetable.dto.univ_requests;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.dto.univ_class.MoveClassDto;
import ru.vsu.cs.timetable.dto.week_time.DayTimes;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@SuperBuilder
public class MoveClassResponse {

    private Map<Integer, List<MoveClassDto>> coursesClasses;
    private Map<Integer, List<DayTimes>> possibleTimesInAudience;
}
