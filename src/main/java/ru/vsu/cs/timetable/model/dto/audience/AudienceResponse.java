package ru.vsu.cs.timetable.model.dto.audience;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Setter
@Getter
@SuperBuilder
@Schema(description = "Аудитория с характеристиками")
public class AudienceResponse {

    @Schema(description = "Номер аудитории", example = "243")
    private Integer audienceNumber;
    @Schema(description = "Размер аудитории", example = "40")
    private Long capacity;
    @Schema(description = "Инвентарь аудитории", example = "[\"Компьютеры\", \"Проектор\"]")
    private Set<String> equipments;
    @Schema(description = "Университет, к которому относится аудитория", example = "Воронежский государственный университет")
    private String university;
    @Schema(description = "Факультет, к которому относится аудитория", example = "Факультет компьютерных наук")
    private String faculty;
}
