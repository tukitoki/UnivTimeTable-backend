package ru.vsu.cs.timetable.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.vsu.cs.timetable.dto.group.GroupDto;
import ru.vsu.cs.timetable.dto.group.GroupPageDto;
import ru.vsu.cs.timetable.dto.group.ShowCreateGroupDto;
import ru.vsu.cs.timetable.dto.page.SortDirection;
import ru.vsu.cs.timetable.entity.Group;

@Validated
public interface GroupService {

    GroupPageDto getFacultyGroups(int currentPage, int pageSize, Integer course,
                                  Integer groupNumber, SortDirection order, @NotNull Long facultyId);

    GroupDto getGroupById(@NotNull Long id);

    Group findGroupById(@NotNull Long id);

    void createGroup(@NotNull @Valid GroupDto groupDto,
                     @NotNull Long facultyId);

    ShowCreateGroupDto showCreateGroup(@NotNull Long facultyId);

    void deleteGroup(@NotNull Long id);

    void updateGroup(@NotNull @Valid GroupDto groupDto,
                     @NotNull Long id);
}
