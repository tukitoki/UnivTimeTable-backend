package ru.vsu.cs.timetable.dto.univ_requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vsu.cs.timetable.dto.group.GroupResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
public class SendRequestDto {

    private String subjectName;
    private GroupResponse groupResponse;
    private BigDecimal subjectHourPerWeek;
    private String typeClass;
    private List<String> equipments;
    private Map<String, List<String>> impossibleTime;
}
