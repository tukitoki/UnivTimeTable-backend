package ru.vsu.cs.timetable.service;

import ru.vsu.cs.timetable.dto.group.CreateGroupRequest;
import ru.vsu.cs.timetable.dto.group.GroupDto;
import ru.vsu.cs.timetable.dto.group.GroupPageDto;
import ru.vsu.cs.timetable.dto.group.ShowCreateGroupDto;
import ru.vsu.cs.timetable.dto.page.SortDirection;
import ru.vsu.cs.timetable.model.Group;

public interface GroupService {

    GroupPageDto getFacultyGroups(int pageNumber, int pageSize, Integer course,
                                  Integer groupNumber, SortDirection order, Long facultyId);

    Group findGroupById(Long id);

    void createGroup(CreateGroupRequest createGroupRequest, Long facultyId);

    ShowCreateGroupDto showCreateGroup(Long facultyId);

    void deleteGroup(Long id);

    void updateGroup(GroupDto groupDto, Long id);
}
