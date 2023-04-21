package ru.vsu.cs.timetable.dto.group;

import ru.vsu.cs.timetable.dto.page.PageModel;

import java.util.List;

public class GroupPageDto {

    private PageModel<GroupDto> groupsPage;
    private List<Integer> courses;
}
