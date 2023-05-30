package ru.vsu.cs.timetable.dto.univ_requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vsu.cs.timetable.dto.group.GroupResponse;
import ru.vsu.cs.timetable.entity.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.entity.enums.TypeClass;

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
