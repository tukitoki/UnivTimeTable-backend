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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.timetable.exception.UserException;
import ru.vsu.cs.timetable.logic.service.FacultyService;
import ru.vsu.cs.timetable.logic.service.GroupService;
import ru.vsu.cs.timetable.logic.service.UniversityService;
import ru.vsu.cs.timetable.logic.service.UserService;
import ru.vsu.cs.timetable.model.dto.page.PageModel;
import ru.vsu.cs.timetable.model.dto.university.UniversityResponse;
import ru.vsu.cs.timetable.model.dto.user.CreateUserResponse;
import ru.vsu.cs.timetable.model.dto.user.UserDto;
import ru.vsu.cs.timetable.model.dto.user.UserPageDto;
import ru.vsu.cs.timetable.model.dto.user.UserResponse;
import ru.vsu.cs.timetable.model.entity.University;
import ru.vsu.cs.timetable.model.entity.User;
import ru.vsu.cs.timetable.model.enums.UserRole;
import ru.vsu.cs.timetable.model.mapper.UniversityMapper;
import ru.vsu.cs.timetable.model.mapper.UserMapper;
import ru.vsu.cs.timetable.repository.ClassRepository;
import ru.vsu.cs.timetable.repository.GroupRepository;
import ru.vsu.cs.timetable.repository.RequestRepository;
import ru.vsu.cs.timetable.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UniversityService universityService;
    private final GroupService groupService;
    private final FacultyService facultyService;
    private final UserMapper userMapper;
    private final UniversityMapper universityMapper;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final RequestRepository requestRepository;
    private final ClassRepository classRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public UserPageDto getAllUsers(int currentPage, int pageSize, String university,
                                   UserRole role, String city, String name) {
        Page<User> page = filerPage(currentPage, pageSize, university, role, city, name);

        List<UserResponse> userResponses = page.getContent()
                .stream()
                .map(userMapper::toResponse)
                .toList();

        List<UserRole> userRoles = getAllRoles();
        List<String> univNames = universityService.findAllUniversities()
                .stream()
                .map(University::getName)
                .toList();
        List<String> userCities = userRepository.findAllUserCities();

        var pageModel = PageModel.of(userResponses, currentPage, page.getTotalElements(),
                pageSize, page.getTotalPages());

        return UserPageDto.builder()
                .usersPage(pageModel)
                .cities(userCities)
                .universities(univNames)
                .roles(userRoles)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getFreeHeadmenByFaculty(Long facultyId) {
        return userRepository.findAllFreeHeadmenByFaculty(facultyId)
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserDtoById(Long id) {
        User user = getUserById(id);

        return userMapper.toDto(user);
    }

    @Override
    public void createUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw UserException.CODE.USERNAME_ALREADY_PRESENT.get();
        }
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw UserException.CODE.EMAIL_ALREADY_PRESENT.get();
        }

        validCreatingUser(userDto.getRole(), userDto.getUniversityId(), userDto.getFacultyId(), userDto.getGroupId());

        var univ = userDto.getUniversityId() == null
                ? null
                : universityService.findUnivById(userDto.getUniversityId());
        var group = userDto.getGroupId() == null
                ? null
                : groupService.findGroupById(userDto.getGroupId());
        var faculty = userDto.getFacultyId() == null
                ? null
                : facultyService.findFacultyById(userDto.getFacultyId());

        String password = passwordEncoder.encode(userDto.getPassword());

        User user = userMapper.toEntity(userDto, univ, group, faculty, password);
        user = userRepository.save(user);

        if (group != null) {
            group.setHeadmanId(user.getId());
            groupRepository.save(group);
        }

        log.info("user: {}, was successfully saved", user);
    }

    @Override
    @Transactional(readOnly = true)
    public CreateUserResponse showCreateUser() {
        List<UserRole> userRoles = getAllRoles();
        List<UniversityResponse> universities = universityService.findAllUniversities()
                .stream()
                .map(universityMapper::toResponse)
                .toList();

        return CreateUserResponse.builder()
                .roles(userRoles)
                .universityResponses(universities)
                .build();
    }

    @Override
    public void updateUser(UserDto userDto, Long id) {
        User oldUser = getUserById(id);

        var optionalUser = userRepository.findByUsername(userDto.getUsername());
        if (optionalUser.isPresent() &&
                !optionalUser.get().getUsername().equals(oldUser.getUsername())) {
            throw UserException.CODE.USERNAME_ALREADY_PRESENT.get();
        }

        optionalUser = userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent() &&
                !optionalUser.get().getUsername().equals(oldUser.getUsername())) {
            throw UserException.CODE.EMAIL_ALREADY_PRESENT.get();
        }

        validCreatingUser(userDto.getRole(), userDto.getUniversityId(), userDto.getFacultyId(), userDto.getGroupId());

        var univ = userDto.getUniversityId() == null
                ? null
                : universityService.findUnivById(userDto.getUniversityId());
        var group = userDto.getGroupId() == null
                ? null
                : groupService.findGroupById(userDto.getGroupId());
        var faculty = userDto.getFacultyId() == null
                ? null
                : facultyService.findFacultyById(userDto.getFacultyId());
        String password = userDto.getPassword() == null
                ? null
                : passwordEncoder.encode(userDto.getPassword());

        if (group != null && oldUser.getGroup() == null) {
            group.setStudentsAmount(group.getStudentsAmount() + 1);
            group.setHeadmanId(oldUser.getId());
            groupRepository.save(group);
        } else if (group == null && oldUser.getGroup() != null) {
            oldUser.getGroup().setStudentsAmount(oldUser.getGroup().getStudentsAmount() - 1);
            oldUser.getGroup().setHeadmanId(null);
            groupRepository.save(oldUser.getGroup());
        } else if (group != null) {
            oldUser.getGroup().setHeadmanId(null);
            oldUser.getGroup().setStudentsAmount(oldUser.getGroup().getStudentsAmount() - 1);
            group.setStudentsAmount(group.getStudentsAmount() + 1);
            group.setHeadmanId(oldUser.getId());
            groupRepository.save(oldUser.getGroup());
            groupRepository.save(group);
        }

        User newUser = userMapper.toEntity(userDto, univ, group, faculty, password);
        if (password == null) {
            BeanUtils.copyProperties(newUser, oldUser, "id", "password");
        } else {
            BeanUtils.copyProperties(newUser, oldUser, "id");
        }

        oldUser = userRepository.save(oldUser);

        log.info("user: {}, was successfully updated", oldUser);
    }

    @Override
    public void deleteUser(Long id, String username) {
        User user = getUserById(id);

        if (user.getUsername().equals(username)) {
            throw UserException.CODE.CANT_DELETE_YOURSELF.get();
        }

        if (user.getGroup() != null) {
            user.getGroup().setStudentsAmount(user.getGroup().getStudentsAmount() - 1);
            groupRepository.save(user.getGroup());
        }

        if (user.getRole() == UserRole.LECTURER) {
            deleteRequests(user);
            deleteClasses(user);
        }

        userRepository.delete(user);

        log.info("user: {}, was successfully deleted", user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserException.CODE.ID_NOT_FOUND::get);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserException.CODE.USERNAME_NOT_FOUND::get);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserException.CODE.EMAIL_NOT_FOUND::get);
    }

    private Page<User> filerPage(int currentPage, int pageSize, String university,
                                 UserRole role, String city, String name) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);

        Root<User> root = query.from(User.class);
        query.select(root).distinct(true);

        List<Predicate> predicates = new ArrayList<>();
        if (university != null) {
            var univ = universityService.findUnivByName(university);
            predicates.add(cb.equal(root.get("university"), univ));
        }
        if (role != null) {
            predicates.add(cb.equal(root.get("role"), role));
        }
        if (city != null) {
            predicates.add(cb.equal(root.get("city"), city));
        }
        if (name != null) {
            predicates.add(cb.like(cb.lower(root.get("fullName")), "%" + name.toLowerCase() + "%"));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        List<Order> orderList = List.of(cb.asc(root.get("id")));
        query.orderBy(orderList);

        TypedQuery<User> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<User> users = typedQuery.getResultList();
        long count = countFilteredUsers(university, role, city, name);

        return new PageImpl<>(users, pageable, count);
    }


    private long countFilteredUsers(String university, UserRole role,
                                    String city, String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<User> root = query.from(User.class);
        query.select(cb.countDistinct(root));
        List<Predicate> predicates = new ArrayList<>();
        if (university != null) {
            var univ = universityService.findUnivByName(university);
            predicates.add(cb.equal(root.get("university"), univ));
        }
        if (role != null) {
            predicates.add(cb.equal(root.get("role"), role));
        }
        if (city != null) {
            predicates.add(cb.equal(root.get("city"), city));
        }
        if (name != null) {
            predicates.add(cb.like(cb.lower(root.get("fullName")), "%" + name.toLowerCase() + "%"));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Long> typedQuery = entityManager.createQuery(query);

        return typedQuery.getSingleResult();
    }

    private List<UserRole> getAllRoles() {
        return Arrays.stream(UserRole.values())
                .toList();
    }

    public void deleteRequests(User lecturer) {
        requestRepository.deleteAll(requestRepository.findAllByLecturer(lecturer));
    }

    public void deleteClasses(User lecturer) {
        classRepository.deleteAll(classRepository.findAllByLecturer(lecturer));
    }

    private void validCreatingUser(UserRole role, Long univId, Long facultyId, Long groupId) {
        if (role == UserRole.ADMIN) {
            if (univId != null) {
                throw UserException.CODE.ADMIN_CANT_HAVE_UNIV.get();
            }
            if (facultyId != null) {
                throw UserException.CODE.ADMIN_CANT_HAVE_FACULTY.get();
            }
            if (groupId != null) {
                throw UserException.CODE.ADMIN_CANT_HAVE_GROUP.get();
            }
        } else if (role == UserRole.LECTURER) {
            if (univId == null) {
                throw UserException.CODE.LECTURER_SHOULD_HAVE_UNIVERSITY.get();
            }
            if (facultyId == null) {
                throw UserException.CODE.LECTURER_SHOULD_HAVE_FACULTY.get();
            }
            if (groupId != null) {
                throw UserException.CODE.LECTURER_CANT_HAVE_GROUP.get();
            }
        } else {
            if (univId == null) {
                throw UserException.CODE.HEADMAN_SHOULD_HAVE_UNIVERSITY.get();
            }
            if (facultyId == null) {
                throw UserException.CODE.HEADMAN_SHOULD_HAVE_FACULTY.get();
            }
        }
    }
}
