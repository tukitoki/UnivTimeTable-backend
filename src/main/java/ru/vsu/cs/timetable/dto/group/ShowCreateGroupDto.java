package ru.vsu.cs.timetable.dto.group;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.dto.user.UserResponse;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class ShowCreateGroupDto {

    private List<UserResponse> userResponses;

}
