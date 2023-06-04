package ru.vsu.cs.timetable.model.dto.univ_requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vsu.cs.timetable.model.dto.univ_class.ClassDto;

@Setter
@Getter
@NoArgsConstructor
public class MoveClassRequest {

    private ClassDto initClass;
    private ClassDto classToMove;
}
