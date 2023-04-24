package ru.vsu.cs.timetable.dto.faculty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.dto.page.PageModel;

@Getter
@Setter
@SuperBuilder
public class FacultyPageDto {

    PageModel<FacultyDto> facultiesPage;
}
