package ru.vsu.cs.timetable.model.dto.univ_requests;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.group.GroupResponse;
import ru.vsu.cs.timetable.model.entity.enums.TypeClass;

import java.util.List;

@Setter
@Getter
@SuperBuilder
public class ShowSendRequest {

    private List<TypeClass> typesOfClass;
    private List<String> equipments;
    private List<GroupResponse> groupsOfCourse;
}
