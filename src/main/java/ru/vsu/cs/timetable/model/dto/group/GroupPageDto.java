package ru.vsu.cs.timetable.model.dto.group;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.page.PageModel;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@Schema(description = "Страница групп с списком курсов")
public class GroupPageDto {

    private PageModel<GroupDto> groupsPage;
    private List<Integer> courses;
}
