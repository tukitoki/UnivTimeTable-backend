package ru.vsu.cs.timetable.model.dto.univ_class;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.model.enums.TypeClass;
import ru.vsu.cs.timetable.model.enums.WeekType;

import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@Schema(description = "Описание пары", example = """
        {
          "subjectName": "Электродинамика",
          "startTime": "13:25",
          "endTime": "15:00",
          "audience": 243,
          "dayOfWeek": "Понедельник",
          "typeOfClass": "Лекция",
          "weekType": "Числитель",
          "courseNumber": 1,
          "groupsNumber": [1, 2],
          "capacity": 200,
          "equipments": ["Компьютеры", "Проектор"]
        }
        """)
public class ClassDto {

    @NotNull
    @NotBlank
    @Schema(description = "Название предмета", example = "Электродинамика")
    private String subjectName;
    @NotNull
    @NotBlank
    @Schema(description = "Изначальное время", example = "13:25")
    private LocalTime startTime;
    @Schema(description = "Конечное время", example = "15:00")
    private LocalTime endTime;
    @NotNull
    @NotBlank
    @Max(value = 2000)
    @Schema(description = "Номер аудитории", example = "243")
    private Integer audience;
    @NotNull
    @NotBlank
    @Schema(description = "День недели", example = "Понедельник")
    private DayOfWeekEnum dayOfWeek;
    @NotNull
    @NotBlank
    @Schema(description = "Тип пары", example = "Лекция")
    private TypeClass typeOfClass;
    @Schema(description = "Тип недели", example = "Числитель")
    private WeekType weekType;
    @Schema(description = "Курс групп", example = "1")
    private Integer courseNumber;
    @Schema(description = "Номера группы", example = "[1, 2]")
    private List<Integer> groupsNumber;
    @Schema(description = "Размер аудитории для проведения пары", example = "200")
    private Integer capacity;
    @Schema(description = "Инвентарь для проведения пары", example = "[\"Компьютеры\", \"Проектор\"]")
    private List<String> equipments;
}
