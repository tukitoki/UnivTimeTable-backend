package ru.vsu.cs.timetable.model.dto.audience;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.week_time.DayTimes;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@SuperBuilder
@Schema(description = "Аудитория с характеристиками и информацией о свободном времни")
public class AudienceToMoveResponse {

    @Schema(description = "Номер аудитории", example = "243")
    private Integer audienceNumber;
    @Schema(description = "Вместимость аудитории", example = "40")
    private Long capacity;
    @Schema(description = "Список свободного времени аудитории")
    private List<DayTimes> dayTimes;
    @Schema(description = "Инвентарь аудитории", example = "[\"Компьютеры\", \"Проектор\"]")
    private Set<String> equipments;
}
