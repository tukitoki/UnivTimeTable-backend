package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.timetable.controller.api.GroupApi;
import ru.vsu.cs.timetable.dto.group.CreateGroupRequest;
import ru.vsu.cs.timetable.dto.group.GroupDto;
import ru.vsu.cs.timetable.dto.group.GroupPageDto;
import ru.vsu.cs.timetable.dto.group.ShowCreateGroupDto;
import ru.vsu.cs.timetable.dto.page.SortDirection;

@RequiredArgsConstructor
@RequestMapping("/university/{univId}/faculty")
@RestController
public class GroupController implements GroupApi {
    @Override
    @GetMapping("/{facultyId}")
    public GroupPageDto getFacultyGroups(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) Integer course,
            @RequestParam(required = false) Integer groupNumber,
            @RequestParam(defaultValue = "ASC") SortDirection order,
            @PathVariable Long facultyId
    ) {
        return null;
    }

    @Override
    @PostMapping("/{facultyId}/group/create")
    public void createGroup(@RequestBody CreateGroupRequest createGroupRequest,
                            @PathVariable Long facultyId) {

    }

    @Override
    @GetMapping("/{facultyId}/group/create")
    public ShowCreateGroupDto showCreateGroup(@PathVariable Long facultyId) {
        return null;
    }

    @Override
    @GetMapping("/{facultyId}/group/{id}")
    public void deleteGroup(@PathVariable Long id) {

    }

    @Override
    @PutMapping("/{facultyId}/group/{id}")
    public void updateGroup(@RequestBody GroupDto groupDto,
                            @PathVariable Long id) {

    }
}
