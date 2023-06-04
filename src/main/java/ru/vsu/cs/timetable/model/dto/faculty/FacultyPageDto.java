package ru.vsu.cs.timetable.model.dto.faculty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.page.PageModel;

@Getter
@Setter
@SuperBuilder
public class FacultyPageDto {

    PageModel<FacultyDto> facultiesPage;
}
