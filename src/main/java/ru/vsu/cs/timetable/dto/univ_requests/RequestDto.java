package ru.vsu.cs.timetable.dto.univ_requests;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.dto.user.UserDto;
import ru.vsu.cs.timetable.entity.enums.TypeClass;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
public class RequestDto {

    private String subjectName;
    private BigDecimal subjectHourPerWeek;
    private TypeClass typeClass;
    private UserDto userDto;
}
