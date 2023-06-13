package ru.vsu.cs.timetable.logic.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.timetable.model.dto.group.GroupDto;
import ru.vsu.cs.timetable.model.dto.group.GroupPageDto;
import ru.vsu.cs.timetable.model.dto.page.PageModel;
import ru.vsu.cs.timetable.model.dto.page.SortDirection;
import ru.vsu.cs.timetable.model.entity.Faculty;
import ru.vsu.cs.timetable.model.entity.Group;
import ru.vsu.cs.timetable.model.entity.User;
import ru.vsu.cs.timetable.exception.GroupException;
import ru.vsu.cs.timetable.exception.UserException;
import ru.vsu.cs.timetable.logic.service.FacultyService;
import ru.vsu.cs.timetable.model.enums.UserRole;
import ru.vsu.cs.timetable.model.mapper.GroupMapper;
import ru.vsu.cs.timetable.repository.ClassRepository;
import ru.vsu.cs.timetable.repository.GroupRepository;
import ru.vsu.cs.timetable.repository.UserRepository;
import ru.vsu.cs.timetable.logic.service.GroupService;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static ru.vsu.cs.timetable.model.dto.page.SortDirection.ASC;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class GroupServiceImpl implements GroupService {

    private final FacultyService facultyService;
    private final GroupRepository groupRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;
    private final GroupMapper groupMapper;
    private final EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public GroupPageDto getFacultyGroups(int currentPage, int pageSize, Integer course,
                                         Integer groupNumber, SortDirection order, Long facultyId) {
        Page<Group> page = filerPage(currentPage, pageSize, course, groupNumber, order, facultyId);

        List<GroupDto> groupDtos = page.getContent()
                .stream()
                .map(groupMapper::toDto)
                .toList();
        List<Integer> courses = groupRepository.findAllCoursesByFaculty(facultyId);

        var pageModel = PageModel.of(groupDtos, currentPage, page.getTotalElements(),
                pageSize, page.getTotalPages());

        return GroupPageDto.builder()
                .groupsPage(pageModel)
                .courses(courses)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public GroupDto getGroupById(Long id) {
        Group group = findGroupById(id);

        return groupMapper.toDto(group);
    }

    @Override
    @Transactional(readOnly = true)
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

        User headman = null;
        if (groupDto.getHeadman() != null) {
            headman = userRepository.findById(groupDto.getHeadman().getId())
                    .orElseThrow(UserException.CODE.ID_NOT_FOUND::get);

            if (headman.getRole() == UserRole.ADMIN) {
                throw UserException.CODE.ADMIN_CANT_HAVE_GROUP.get();
            }
        }

        Group group = Group.builder()
                .studentsAmount(groupDto.getStudentsAmount())
                .courseNumber(groupDto.getCourseNumber())
                .groupNumber(groupDto.getGroupNumber())
                .headmanId(groupDto.getHeadman().getId())
                .faculty(faculty)
                .users(new ArrayList<>())
                .classes(new LinkedHashSet<>())
                .build();

        group = groupRepository.save(group);

        if (headman != null) {
            headman.setGroup(group);
            userRepository.save(headman);
        }

        log.info("group: {} was successfully saved", group);
    }

    @Override
    public void deleteGroup(Long id) {
        Group group = findGroupById(id);

        classRepository.findAllByGroupsContains(group).forEach(aClass -> {
            if (aClass.getGroups().size() == 1) {
                classRepository.delete(aClass);
            }
        });

        groupRepository.delete(group);

        log.info("group was successfully deleted {}", group);
    }

    @Override
    public void updateGroup(GroupDto groupDto, Long id) {
        Group oldGroup = findGroupById(id);
        Group newGroup;
        List<String> ignoreProperties = new ArrayList<>(List.of("id", "faculty", "classes"));

        if (groupDto.getHeadman() == null) {
            newGroup = groupMapper.toEntity(groupDto);
            if (oldGroup.getHeadmanId() != null) {
                var headman = userRepository.findById(oldGroup.getHeadmanId())
                        .orElseThrow(UserException.CODE.ID_NOT_FOUND::get);
                headman.setGroup(null);
                userRepository.save(headman);
            }
            newGroup.setUsers(new ArrayList<>());
        } else if (groupDto.getHeadman().getId().equals(oldGroup.getHeadmanId())) {
            ignoreProperties.add("users");
            newGroup = groupMapper.toEntity(groupDto);
        } else {
            User headman = userRepository.findById(groupDto.getHeadman().getId())
                    .orElseThrow(UserException.CODE.ID_NOT_FOUND::get);
            headman.setGroup(oldGroup);
            userRepository.save(headman);

            newGroup = groupMapper.toEntity(groupDto, headman);
        }

        var optionalGroup = groupRepository.findByFacultyAndGroupNumberAndCourseNumber(oldGroup.getFaculty(),
                newGroup.getGroupNumber(), newGroup.getGroupNumber());
        if (optionalGroup.isPresent() && !oldGroup.getGroupNumber().equals(newGroup.getGroupNumber())
                && !oldGroup.getCourseNumber().equals(newGroup.getGroupNumber())) {
            throw GroupException.CODE.GROUP_FACULTY_ALREADY_PRESENT.get();
        }

        BeanUtils.copyProperties(newGroup, oldGroup, ignoreProperties.toArray(String[]::new));

        oldGroup = groupRepository.save(oldGroup);

        log.info("group was successfully updated {}", oldGroup);
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
