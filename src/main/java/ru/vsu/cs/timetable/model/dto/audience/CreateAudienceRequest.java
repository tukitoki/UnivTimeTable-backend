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
    @Schema(description = "Номер аудитории", example = "341")
    private Integer audienceNumber;
    @Positive
    @NotNull
    @Schema(description = "Размер аудитории", example = "30")
    private Long capacity;
    @Schema(description = "Список инвентаря", example = "[\"Компьютер\", \"Проектор\"]")
    private List<String> equipments;
}
