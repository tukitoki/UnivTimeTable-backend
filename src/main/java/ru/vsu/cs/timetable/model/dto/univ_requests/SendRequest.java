package ru.vsu.cs.timetable.model.dto.univ_requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vsu.cs.timetable.model.dto.group.GroupResponse;
import ru.vsu.cs.timetable.model.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.model.enums.TypeClass;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Информация для подачи заявки")
public class SendRequest {

    @Schema(description = "Название предмета", example = "Электродинамика")
    private String subjectName;
    @Schema(description = "Группа для проведения пары")
    private GroupResponse groupResponse;
    @Schema(description = "Количество часов в неделю", example = "1.5")
    private BigDecimal subjectHourPerWeek;
    @Schema(description = "Тип пары", example = "Лекция")
    private TypeClass typeClass;
    @Schema(description = "Инвентарь, нужный для проведения пары", example = "[\"Компьютеры\", \"Проектор\"]")
    private List<String> equipments;
    @Schema(description = "Невозможное время", example = """
            {
              "Понедельник": ["09:45", "11:30"],
              "Вторник": ["13:25", "16:55"]
            }
            """
    )
    private Map<DayOfWeekEnum, List<LocalTime>> impossibleTime;
}
