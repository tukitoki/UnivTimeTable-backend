package ru.vsu.cs.timetable.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.univ_class.ClassDto;
import ru.vsu.cs.timetable.model.entity.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.model.entity.enums.WeekType;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@SuperBuilder
@Schema(description = "Расписание пользователя")
public class TimetableResponse {

    private Map<WeekType, Map<DayOfWeekEnum, List<ClassDto>>> classes;

    public int countTotalClassesSize() {
        int totalSize = 0;
        for (var entry : classes.entrySet()) {
            for (var classesDay : entry.getValue().entrySet()) {
                totalSize += classesDay.getValue().size();
            }
        }
        return totalSize;
    }
}
