package ru.vsu.cs.timetable.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.dto.univ_class.ClassDto;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@SuperBuilder
public class TimetableResponse {

    private Map<String, Map<String, List<ClassDto>>> classes;

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
