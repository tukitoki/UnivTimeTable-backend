package ru.vsu.cs.timetable.model.dto.audience;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Информация для создания аудитории")
public class CreateAudienceRequest {

    @Positive
    @NotNull
    private Integer audienceNumber;
    @Positive
    @NotNull
    private Long capacity;
    private List<String> equipments;
}
