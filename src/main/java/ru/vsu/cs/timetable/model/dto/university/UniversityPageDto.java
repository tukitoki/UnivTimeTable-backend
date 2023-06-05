package ru.vsu.cs.timetable.model.dto.university;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.page.PageModel;

@Setter
@Getter
@SuperBuilder
@Schema(description = "Страница с университетами")
public class UniversityPageDto {

    private PageModel<UniversityDto> universitiesPage;
}
