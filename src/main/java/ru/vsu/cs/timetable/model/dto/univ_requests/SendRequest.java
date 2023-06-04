package ru.vsu.cs.timetable.model.dto.univ_requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vsu.cs.timetable.model.dto.group.GroupResponse;
import ru.vsu.cs.timetable.model.entity.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.model.entity.enums.TypeClass;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
public class SendRequest {

    private String subjectName;
    private GroupResponse groupResponse;
    private BigDecimal subjectHourPerWeek;
    private TypeClass typeClass;
    private List<String> equipments;
    private Map<DayOfWeekEnum, List<LocalTime>> impossibleTime;
}
