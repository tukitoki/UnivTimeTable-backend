package ru.vsu.cs.timetable.model.dto.univ_requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.user.UserDto;
import ru.vsu.cs.timetable.model.enums.TypeClass;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@Schema(description = "Информация о заявке")
public class RequestDto {

    @Schema(description = "Название предмета", example = "Электродинамика")
    private String subjectName;
    @Schema(description = "Количество часов в неделю", example = "1.5")
    private BigDecimal subjectHourPerWeek;
    @Schema(description = "Тип пары", example = "Лекция")
    private TypeClass typeClass;
    @Schema(description = "Информация о преподавателе")
    private UserDto userDto;
}
