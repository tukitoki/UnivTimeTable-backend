package ru.vsu.cs.timetable.dto.audience;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class CreateAudienceRequest {

    @Positive
    @NotNull
    private Integer audienceNumber;
    @Positive
    @NotNull
    private Long capacity;
    private List<String> equipments;
}
