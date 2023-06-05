package ru.vsu.cs.timetable.model.dto.univ_requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.group.GroupResponse;
import ru.vsu.cs.timetable.model.entity.enums.TypeClass;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@Schema(description = "Информация, чтобы преподаватель смог подать заявку")
public class ShowSendRequest {

    private List<TypeClass> typesOfClass;
    private List<String> equipments;
    private List<GroupResponse> groupsOfCourse;
}
