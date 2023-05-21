package ru.vsu.cs.timetable.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.timetable.dto.group.GroupDto;
import ru.vsu.cs.timetable.dto.group.GroupPageDto;
import ru.vsu.cs.timetable.dto.group.ShowCreateGroupDto;
import ru.vsu.cs.timetable.dto.page.PageModel;
import ru.vsu.cs.timetable.dto.page.SortDirection;
import ru.vsu.cs.timetable.exception.GroupException;
import ru.vsu.cs.timetable.exception.UserException;
import ru.vsu.cs.timetable.mapper.GroupMapper;
import ru.vsu.cs.timetable.mapper.UserMapper;
import ru.vsu.cs.timetable.model.Faculty;
import ru.vsu.cs.timetable.model.Group;
import ru.vsu.cs.timetable.model.User;
import ru.vsu.cs.timetable.repository.GroupRepository;
import ru.vsu.cs.timetable.repository.UserRepository;
import ru.vsu.cs.timetable.service.FacultyService;
import ru.vsu.cs.timetable.service.GroupService;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static ru.vsu.cs.timetable.dto.page.SortDirection.ASC;

@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final FacultyService facultyService;
    private final GroupMapper groupMapper;
    private final UserMapper userMapper;
    private final EntityManager entityManager;

    @Override
    public GroupPageDto getFacultyGroups(int currentPage, int pageSize, Integer course,
                                         Integer groupNumber, SortDirection order, Long facultyId) {
        Page<Group> page = filerPage(currentPage, pageSize, course, groupNumber, order, facultyId);

        List<GroupDto> groupDtos = page.getContent()
                .stream()
                .map(groupMapper::toDto)
                .toList();
        List<Integer> courses = groupRepository.findAllCourses();

        var pageModel = PageModel.of(groupDtos, currentPage, page.getTotalElements(),
                pageSize, page.getTotalPages());

        return GroupPageDto.builder()
                .groupsPage(pageModel)
                .courses(courses)
                .build();
    }

    @Override
    public GroupDto getGroupById(Long id) {
        Group group = findGroupById(id);

        return groupMapper.toDto(group);
    }

    @Override
    public Group findGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(GroupException.CODE.ID_NOT_FOUND::get);
    }

    @Override
    public void createGroup(GroupDto groupDto, Long facultyId) {
        Faculty faculty = facultyService.findFacultyById(facultyId);

        if (groupRepository.findByFacultyAndGroupNumberAndCourseNumber(faculty,
                groupDto.getGroupNumber(), groupDto.getCourseNumber()).isPresent()) {
            throw GroupException.CODE.GROUP_FACULTY_ALREADY_PRESENT.get();
        }
        if (groupDto.getHeadmanId() != null) {
            userRepository.findById(groupDto.getHeadmanId())
                    .orElseThrow(UserException.CODE.ID_NOT_FOUND::get);
        }

        Group group = Group.builder()
                .studentsAmount(groupDto.getStudentsAmount())
                .courseNumber(groupDto.getCourseNumber())
                .groupNumber(groupDto.getGroupNumber())
                .headmanId(groupDto.getHeadmanId())
                .faculty(faculty)
                .users(new ArrayList<>())
                .classes(new LinkedHashSet<>())
                .build();

        groupRepository.save(group);
    }

    @Override
    public ShowCreateGroupDto showCreateGroup(Long facultyId) {
        var userResponses = userRepository.findAllFreeHeadmen()
                .stream()
                .map(userMapper::toResponse)
                .toList();

        return ShowCreateGroupDto.builder()
                .userResponses(userResponses)
                .build();
    }

    @Override
    public void deleteGroup(Long id) {
        Group group = findGroupById(id);

        groupRepository.delete(group);
    }

    @Override
    @Transactional
    public void updateGroup(GroupDto groupDto, Long id) {
        Group oldGroup = findGroupById(id);
        Group newGroup;
        List<String> ignoreProperties = new ArrayList<>(List.of("id", "faculty", "classes"));

        if (groupDto.getHeadmanId() == null) {
            newGroup = groupMapper.toEntity(groupDto);
            newGroup.setUsers(new ArrayList<>());
        } else if (groupDto.getHeadmanId().equals(oldGroup.getHeadmanId())){
            ignoreProperties.add("users");
            newGroup = groupMapper.toEntity(groupDto);
        } else {
            User headman = userRepository.findById(groupDto.getHeadmanId())
                    .orElseThrow(UserException.CODE.ID_NOT_FOUND::get);
            headman.setGroup(oldGroup);
            userRepository.save(headman);

            newGroup = groupMapper.toEntity(groupDto, headman);
        }

        BeanUtils.copyProperties(newGroup, oldGroup, ignoreProperties.toArray(String[]::new));

        groupRepository.save(oldGroup);
    }

    private Page<Group> filerPage(int currentPage, int pageSize, Integer course,
                                  Integer groupNumber, SortDirection order, Long facultyId) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> query = cb.createQuery(Group.class);

        Root<Group> root = query.from(Group.class);
        Join<Group, Faculty> faculty = root.join("faculty");

        query.select(root).distinct(true);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(faculty.get("id"), facultyId));

        if (course != null) {
            predicates.add(cb.equal(root.get("courseNumber"), course));
        }
        if (groupNumber != null) {
            predicates.add(cb.equal(root.get("groupNumber"), groupNumber));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        Path<Object> name = root.get("courseNumber");
        Order courseOrder = order.equals(ASC)
                ? cb.asc(name)
                : cb.desc(name);

        List<Order> orderList = List.of(courseOrder, cb.asc(root.get("id")));
        query.orderBy(orderList);

        TypedQuery<Group> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Group> universities = typedQuery.getResultList();

        long count = countFilteredGroups(course, groupNumber, facultyId);

        return new PageImpl<>(universities, pageable, count);
    }

    private long countFilteredGroups(Integer course, Integer groupNumber, Long facultyId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<Group> root = query.from(Group.class);
        Join<Group, Faculty> faculty = root.join("faculty");
        query.select(cb.countDistinct(root));

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(faculty.get("id"), facultyId));

        if (course != null) {
            predicates.add(cb.equal(root.get("courseNumber"), course));
        }
        if (groupNumber != null) {
            predicates.add(cb.equal(root.get("groupNumber"), groupNumber));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Long> typedQuery = entityManager.createQuery(query);

        return typedQuery.getSingleResult();
    }
}
