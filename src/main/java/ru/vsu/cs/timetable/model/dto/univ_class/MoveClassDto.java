package ru.vsu.cs.timetable.model.dto.univ_class;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@Schema(description = "Информация о предмете и группах")
public class MoveClassDto {

    private Set<Integer> groups;
    private List<ClassDto> groupClasses;
}
