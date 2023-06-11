package ru.vsu.cs.timetable.model.dto.faculty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.page.PageModel;

@Getter
@Setter
@SuperBuilder
@Schema(description = "Страница с факультетами")
public class FacultyPageDto {

    @Schema(description = "Страница с факультетами")
    PageModel<FacultyDto> facultiesPage;
}
