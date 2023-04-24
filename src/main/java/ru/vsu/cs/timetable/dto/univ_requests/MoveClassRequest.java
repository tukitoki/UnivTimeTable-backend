package ru.vsu.cs.timetable.dto.univ_requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.vsu.cs.timetable.dto.ClassDto;

@Setter
@Getter
@AllArgsConstructor
public class MoveClassRequest {

    private ClassDto from;
    private ClassDto to;
}
