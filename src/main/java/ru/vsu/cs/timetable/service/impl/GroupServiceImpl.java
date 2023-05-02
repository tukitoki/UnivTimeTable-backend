package ru.vsu.cs.timetable.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.timetable.dto.group.CreateGroupRequest;
import ru.vsu.cs.timetable.dto.group.GroupDto;
import ru.vsu.cs.timetable.dto.group.GroupPageDto;
import ru.vsu.cs.timetable.dto.group.ShowCreateGroupDto;
import ru.vsu.cs.timetable.dto.page.SortDirection;
import ru.vsu.cs.timetable.exception.GroupException;
import ru.vsu.cs.timetable.model.Faculty;
import ru.vsu.cs.timetable.model.Group;
import ru.vsu.cs.timetable.repository.GroupRepository;
import ru.vsu.cs.timetable.service.FacultyService;
import ru.vsu.cs.timetable.service.GroupService;

@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final FacultyService facultyService;

    @Override
    public GroupPageDto getFacultyGroups(int pageNumber, int pageSize, Integer course,
                                         Integer groupNumber, SortDirection order, Long facultyId) {
        return null;
    }

    @Override
    public Group findGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(GroupException.CODE.ID_NOT_FOUND::get);
    }

    @Override
    public void createGroup(CreateGroupRequest createGroupRequest, Long facultyId) {
        Faculty faculty = facultyService.findFacultyById(facultyId);

        if (groupRepository.findByFacultyAndGroupNumberAndCourseNumber(faculty,
                createGroupRequest.getGroupNumber(), createGroupRequest.getCourseNumber()).isPresent()) {
            throw GroupException.CODE.GROUP_FACULTY_ALREADY_PRESENT.get();
        }
        int studentsAmount = 0;
        if (createGroupRequest.getHeadmanId() != null) {
            studentsAmount++;
        }

        Group group = Group.builder()
                .studentsAmount(studentsAmount)
                .courseNumber(createGroupRequest.getCourseNumber())
                .groupNumber(createGroupRequest.getGroupNumber())
                .headmanId(createGroupRequest.getHeadmanId())
                .faculty(faculty)
                .build();
        groupRepository.save(group);
    }

    @Override
    public ShowCreateGroupDto showCreateGroup(Long facultyId) {
        return null;
    }

    @Override
    public void deleteGroup(Long id) {

    }

    @Override
    public void updateGroup(GroupDto groupDto, Long id) {

    }
}
