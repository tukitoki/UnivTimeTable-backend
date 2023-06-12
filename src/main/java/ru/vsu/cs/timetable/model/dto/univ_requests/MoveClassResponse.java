package ru.vsu.cs.timetable.model.dto.univ_requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.audience.AudienceToMoveResponse;
import ru.vsu.cs.timetable.model.dto.univ_class.ClassDto;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@SuperBuilder
@Schema(description = "Информация для того, чтобы преподаватель мог перенести пару")
public class MoveClassResponse {

    @Schema(description = "Пары конкретного курса", example = """
            {
              "1": [
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
              ],
              "2": [
                {
                  "subjectName": "Механика",
                  "startTime": "09:45",
                  "endTime": "11:20",
                  "audience": 123,
                  "dayOfWeek": "Вторник",
                  "typeOfClass": "Семинар",
                  "weekType": "Знаменатель",
                  "courseNumber": 2,
                  "groupsNumber": [3, 4],
                  "capacity": 150,
                  "equipments": ["Проектор"]
                }
              ]
            }
            """
    )
    private Map<Integer, List<ClassDto>> coursesClasses;
    @Schema(description = "Возможные аудитории для переноса")
    private List<AudienceToMoveResponse> audienceToMoveResponses;
}
