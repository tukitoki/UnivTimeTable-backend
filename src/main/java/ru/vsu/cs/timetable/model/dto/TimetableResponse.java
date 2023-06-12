package ru.vsu.cs.timetable.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.univ_class.ClassDto;
import ru.vsu.cs.timetable.model.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.model.enums.WeekType;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@SuperBuilder
@Schema(description = "Расписание пользователя")
public class TimetableResponse {

    @Schema(description = "Пары пользовтеля", example = """
            {
              "Числитель": {
                "Понедельник": [
                  {
                    "subjectName": "Электродинамика",
                    "startTime": "13:25:00",
                    "endTime": "15:00:00",
                    "audience": 243,
                    "dayOfWeek": "Понедельник",
                    "typeOfClass": "Лекция",
                    "weekType": "Числитель",
                    "courseNumber": 1,
                    "groupsNumber": [1, 2],
                    "capacity": 200,
                    "equipments": ["Компьютеры", "Проектор"]
                  }
                ],
                "Вторник": [
                  {
                    "subjectName": "Механика",
                    "startTime": "09:45:00",
                    "endTime": "11:30:00",
                    "audience": 123,
                    "dayOfWeek": "Вторник",
                    "typeOfClass": "Семинар",
                    "weekType": "Числитель",
                    "courseNumber": 2,
                    "groupsNumber": [3, 4],
                    "capacity": 150,
                    "equipments": ["Проектор"]
                  }
                ]
              },
              "Знаменатель": {
                "Среда": [
                  {
                    "subjectName": "Физика",
                    "startTime": "15:10:00",
                    "endTime": "16:45:00",
                    "audience": 321,
                    "dayOfWeek": "Среда",
                    "typeOfClass": "Семинар",
                    "weekType": "Знаменатель",
                    "courseNumber": 1,
                    "groupsNumber": [5, 6],
                    "capacity": 30,
                    "equipments": ["Проектор"]
                  }
                ]
              }
            }
            """
    )
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
