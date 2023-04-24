package ru.vsu.cs.timetable.dto.audience;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class CreateAudienceRequest {

    private Integer audienceNumber;
    private Long capacity;
    private List<String> equipments;
}
