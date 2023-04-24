package ru.vsu.cs.timetable.dto.university;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.dto.page.PageModel;

@Setter
@Getter
@SuperBuilder
public class UniversityPageDto {

    PageModel<UniversityDto> universitiesPage;
}
