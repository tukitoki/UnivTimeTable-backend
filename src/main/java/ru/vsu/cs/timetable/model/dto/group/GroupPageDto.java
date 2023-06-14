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

    @Schema(description = "Страница с группами факультета")
    private PageModel<GroupDto> groupsPage;
    @Schema(description = "Курсы факультета", example = "[\"1\", \"2\", \"3\"]")
    private List<Integer> courses;
}
