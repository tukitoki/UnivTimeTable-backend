package ru.vsu.cs.timetable.model.dto.university;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.page.PageModel;

@Setter
@Getter
@SuperBuilder
public class UniversityPageDto {

    private PageModel<UniversityDto> universitiesPage;
}
