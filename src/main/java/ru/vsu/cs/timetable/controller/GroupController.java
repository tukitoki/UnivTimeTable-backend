package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.timetable.controller.api.GroupApi;
import ru.vsu.cs.timetable.dto.group.CreateGroupRequest;
import ru.vsu.cs.timetable.dto.group.GroupDto;
import ru.vsu.cs.timetable.dto.group.GroupPageDto;
import ru.vsu.cs.timetable.dto.group.ShowCreateGroupDto;
import ru.vsu.cs.timetable.dto.page.SortDirection;
import ru.vsu.cs.timetable.service.GroupService;

@RequiredArgsConstructor
@RequestMapping("/faculty")
@RestController
public class GroupController implements GroupApi {

    private final GroupService groupService;

    @Override
    @GetMapping("/{facultyId}/groups")
    public GroupPageDto getFacultyGroups(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) Integer course,
            @RequestParam(required = false) Integer groupNumber,
            @RequestParam(defaultValue = "ASC") SortDirection order,
            @PathVariable Long facultyId
    ) {
        return groupService.getFacultyGroups(pageNumber, pageSize, course, groupNumber, order, facultyId);
    }

    @Override
    @PostMapping("/{facultyId}/group")
    public void createGroup(@RequestBody CreateGroupRequest createGroupRequest,
                            @PathVariable Long facultyId) {
        groupService.createGroup(createGroupRequest, facultyId);
    }

    @Override
    @GetMapping("/{facultyId}/group")
    public ShowCreateGroupDto showCreateGroup(@PathVariable Long facultyId) {
        return groupService.showCreateGroup(facultyId);
    }

    @Override
    @GetMapping("/group/{id}")
    public void deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
    }

    @Override
    @PutMapping("/group/{id}")
    public void updateGroup(@RequestBody GroupDto groupDto,
                            @PathVariable Long id) {
        groupService.updateGroup(groupDto, id);
    }
}
