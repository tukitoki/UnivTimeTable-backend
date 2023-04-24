package ru.vsu.cs.timetable.dto.univ_requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
public class SendRequestDto {

    private String subjectName;
    private Integer course;
    private Integer group;
    private Integer subjectHourPerWeek;
    private String typeClass;
    private List<String> equipments;
    private Map<String, List<Time>> impossibleTime;
}
