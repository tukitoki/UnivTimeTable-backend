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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.timetable.dto.page.PageModel;
import ru.vsu.cs.timetable.dto.university.UniversityDto;
import ru.vsu.cs.timetable.dto.user.CreateUserResponse;
import ru.vsu.cs.timetable.dto.user.ShowUserResponse;
import ru.vsu.cs.timetable.dto.user.UserDto;
import ru.vsu.cs.timetable.dto.user.UserResponse;
import ru.vsu.cs.timetable.exception.UserException;
import ru.vsu.cs.timetable.mapper.UniversityMapper;
import ru.vsu.cs.timetable.mapper.UserMapper;
import ru.vsu.cs.timetable.entity.University;
import ru.vsu.cs.timetable.entity.User;
import ru.vsu.cs.timetable.entity.enums.UserRole;
import ru.vsu.cs.timetable.repository.GroupRepository;
import ru.vsu.cs.timetable.repository.UserRepository;
import ru.vsu.cs.timetable.service.FacultyService;
import ru.vsu.cs.timetable.service.GroupService;
import ru.vsu.cs.timetable.service.UniversityService;
import ru.vsu.cs.timetable.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UniversityService universityService;
    private final GroupService groupService;
    private final FacultyService facultyService;
    private final UserMapper userMapper;
    private final UniversityMapper universityMapper;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public ShowUserResponse getAllUsers(int currentPage, int pageSize, List<String> universities,
                                        List<String> roles, List<String> cities, String name) {
        Page<User> page = filerPage(currentPage, pageSize, universities, roles, cities, name);

        List<UserResponse> userResponses = page.getContent()
                .stream()
                .map(userMapper::toResponse)
                .toList();

        List<String> userRoles = getAllRoles();
        List<String> univNames = universityService.findAllUniversities()
                .stream()
                .map(University::getName)
                .toList();
        List<String> userCities = userRepository.findAllUserCities();

        var pageModel = PageModel.of(userResponses, currentPage, page.getTotalElements(),
                pageSize, page.getTotalPages());

        return ShowUserResponse.builder()
                .usersPage(pageModel)
                .cities(userCities)
                .universities(univNames)
                .roles(userRoles)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserDtoById(Long id) {
        User user = getUserById(id);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public void createUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw UserException.CODE.USERNAME_ALREADY_PRESENT.get();
        }
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw UserException.CODE.EMAIL_ALREADY_PRESENT.get();
        }

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
    }

    @Override
    @Transactional(readOnly = true)
    public CreateUserResponse showCreateUser() {
        List<String> userRoles = getAllRoles();
        List<UniversityDto> universities = universityService.findAllUniversities()
                .stream()
                .map(universityMapper::toDto)
                .toList();

        return CreateUserResponse.builder()
                .roles(userRoles)
                .universityDtos(universities)
                .build();
    }

    @Override
    @Transactional
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
            groupRepository.save(group);
        } else if (group == null && oldUser.getGroup() != null) {
            oldUser.getGroup().setStudentsAmount(oldUser.getGroup().getStudentsAmount() - 1);
            groupRepository.save(oldUser.getGroup());
        }

        User newUser = userMapper.toEntity(userDto, univ, group, faculty, password);
        if (password == null) {
            BeanUtils.copyProperties(newUser, oldUser, "id", "password");
        } else {
            BeanUtils.copyProperties(newUser, oldUser, "id");
        }
        userRepository.save(oldUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);
        if (user.getGroup() != null) {
            user.getGroup().setStudentsAmount(user.getGroup().getStudentsAmount() - 1);
            groupRepository.save(user.getGroup());
        }

        userRepository.delete(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserException.CODE.ID_NOT_FOUND::get);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserException.CODE.USERNAME_NOT_FOUND::get);
    }

    private Page<User> filerPage(int currentPage, int pageSize, List<String> universities,
                                 List<String> roles, List<String> cities, String name) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);

        Root<User> root = query.from(User.class);
        query.select(root).distinct(true);

        List<Predicate> predicates = new ArrayList<>();
        if (universities != null) {
            universities.forEach(university -> {
                var univ = universityService.findUnivByName(university);
                predicates.add(cb.equal(root.get("university"), univ));
            });
        }
        if (roles != null) {
            roles.forEach(role -> {
                var userRole = UserRole.valueOf(role);
                predicates.add(cb.equal(root.get("role"), userRole));
            });
        }
        if (cities != null) {
            cities.forEach(city -> predicates.add(cb.equal(root.get("city"), city)));
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
        long count = countFilteredUsers(universities, roles, cities, name);

        return new PageImpl<>(users, pageable, count);
    }


    private long countFilteredUsers(List<String> universities, List<String> roles,
                                    List<String> cities, String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<User> root = query.from(User.class);
        query.select(cb.countDistinct(root));
        List<Predicate> predicates = new ArrayList<>();
        if (universities != null) {
            universities.forEach(university -> {
                var univ = universityService.findUnivByName(university);
                predicates.add(cb.equal(root.get("university"), univ));
            });
        }
        if (roles != null) {
            roles.forEach(role -> {
                var userRole = UserRole.valueOf(role);
                predicates.add(cb.equal(root.get("role"), userRole));
            });
        }
        if (cities != null) {
            cities.forEach(city -> predicates.add(cb.equal(root.get("city"), city)));
        }
        if (name != null) {
            predicates.add(cb.like(cb.lower(root.get("fullName")), "%" + name.toLowerCase() + "%"));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Long> typedQuery = entityManager.createQuery(query);

        return typedQuery.getSingleResult();
    }

    private List<String> getAllRoles() {
        return Arrays.stream(UserRole.values())
                .map(Enum::name)
                .toList();
    }
}
