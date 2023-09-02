package ru.vsu.cs.timetable.model.dto.audience;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@Schema(description = "Аудитория с характеристиками")
public class AudienceDto {

    @Schema(description = "Id аудитории", example = "1")
    private Long id;
    @Schema(description = "Номер аудитории", example = "243")
    private Integer audienceNumber;
    @Positive
    @Max(value = 500)
    @Schema(description = "Вместимость аудитории", example = "40")
    private Long capacity;
    @Schema(description = "Инвентарь аудитории", example = "[\"Компьютеры\", \"Проектор\"]")
    private Set<String> equipments;
}
