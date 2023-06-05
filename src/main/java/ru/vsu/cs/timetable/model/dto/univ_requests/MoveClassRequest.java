package ru.vsu.cs.timetable.model.dto.univ_requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vsu.cs.timetable.model.dto.univ_class.ClassDto;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Информация для переноса пары")
public class MoveClassRequest {

    private ClassDto initClass;
    private ClassDto classToMove;
}
