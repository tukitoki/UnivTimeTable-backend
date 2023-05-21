package ru.vsu.cs.timetable.dto.univ_requests;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.dto.group.GroupResponse;

import java.util.List;

@Setter
@Getter
@SuperBuilder
public class ShowSendRequestDto {

    private List<String> typesOfClass;
    private List<String> equipments;
    private List<GroupResponse> groupsOfCourse;
}
