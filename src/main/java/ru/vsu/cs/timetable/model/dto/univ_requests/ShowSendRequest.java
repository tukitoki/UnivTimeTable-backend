package ru.vsu.cs.timetable.model.dto.univ_requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.group.GroupResponse;
import ru.vsu.cs.timetable.model.enums.TypeClass;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@Schema(description = "Информация, чтобы преподаватель смог подать заявку")
public class ShowSendRequest {

    @Schema(description = "Типы пар", example = "[\"Лекция\",\"Семинар\"]")
    private List<TypeClass> typesOfClass;
    @Schema(description = "Возможный инвентарь", example = "[\"Компьютеры\",\"Проектор\"]")
    private List<String> equipments;
    @Schema(description = "Группы по курсам")
    private List<GroupResponse> groupsOfCourse;
}
