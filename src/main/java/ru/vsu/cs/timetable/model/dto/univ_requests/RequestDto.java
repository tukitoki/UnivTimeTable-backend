package ru.vsu.cs.timetable.model.dto.univ_requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.user.UserDto;
import ru.vsu.cs.timetable.model.entity.enums.TypeClass;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@Schema(description = "Информация о заявке")
public class RequestDto {

    private String subjectName;
    private BigDecimal subjectHourPerWeek;
    private TypeClass typeClass;
    private UserDto userDto;
}
