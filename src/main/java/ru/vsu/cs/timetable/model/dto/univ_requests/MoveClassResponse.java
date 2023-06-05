package ru.vsu.cs.timetable.model.dto.univ_requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.univ_class.MoveClassDto;
import ru.vsu.cs.timetable.model.dto.week_time.DayTimes;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@SuperBuilder
@Schema(description = "Информация для того, чтобы преподаватель мог перенести пару")
public class MoveClassResponse {

    private Map<Integer, List<MoveClassDto>> coursesClasses;
    private Map<Integer, List<DayTimes>> possibleTimesInAudience;
}
