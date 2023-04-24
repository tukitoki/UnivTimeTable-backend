package ru.vsu.cs.timetable.dto.audience;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class CreateAudienceResponse {

    private List<String> equipments;
}
