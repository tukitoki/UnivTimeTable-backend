package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.timetable.controller.api.GroupApi;
import ru.vsu.cs.timetable.model.dto.group.GroupDto;
import ru.vsu.cs.timetable.model.dto.group.GroupPageDto;
import ru.vsu.cs.timetable.model.dto.page.SortDirection;
import ru.vsu.cs.timetable.logic.service.GroupService;

@RequiredArgsConstructor
@PreAuthorize("hasAuthority('CREATE_GROUP_AUTHORITY')")
@RequestMapping("/faculty")
@RestController
public class GroupController implements GroupApi {

    private final GroupService groupService;

    @Override
    @GetMapping("/{facultyId}/groups")
    public ResponseEntity<GroupPageDto> getFacultyGroups(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) Integer course,
            @RequestParam(required = false) Integer groupNumber,
            @RequestParam(defaultValue = "ASC") SortDirection order,
            @PathVariable Long facultyId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(groupService.getFacultyGroups(currentPage, pageSize, course, groupNumber, order, facultyId));
    }

    @Override
    @GetMapping("/group/{id}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(groupService.getGroupById(id));
    }

    @Override
    @PostMapping("/{facultyId}/group")
    public ResponseEntity<Void> createGroup(@RequestBody GroupDto groupDto,
                                            @PathVariable Long facultyId) {
        groupService.createGroup(groupDto, facultyId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    @DeleteMapping("/group/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    @PutMapping("/group/{id}")
    public ResponseEntity<Void> updateGroup(@RequestBody GroupDto groupDto,
                                            @PathVariable Long id) {
        groupService.updateGroup(groupDto, id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
