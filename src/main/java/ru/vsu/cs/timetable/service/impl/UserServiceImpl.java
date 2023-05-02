package ru.vsu.cs.timetable.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vsu.cs.timetable.dto.user.CreateUserRequest;
import ru.vsu.cs.timetable.dto.user.CreateUserResponse;
import ru.vsu.cs.timetable.dto.user.ShowUserResponse;
import ru.vsu.cs.timetable.dto.user.UserDto;
import ru.vsu.cs.timetable.exception.UserException;
import ru.vsu.cs.timetable.mapper.UserMapper;
import ru.vsu.cs.timetable.model.User;
import ru.vsu.cs.timetable.repository.UserRepository;
import ru.vsu.cs.timetable.service.FacultyService;
import ru.vsu.cs.timetable.service.GroupService;
import ru.vsu.cs.timetable.service.UniversityService;
import ru.vsu.cs.timetable.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UniversityService universityService;
    private final GroupService groupService;
    private final FacultyService facultyService;
    private final UserMapper userMapper;

    @Override
    public ShowUserResponse getAllUsers(int pageNumber, int pageSize, List<String> universities,
                                        List<String> roles, List<String> cities, String name) {
        return null;
    }

    @Override
    public void createUser(CreateUserRequest createUserRequest) {
        if (userRepository.findByUsername(createUserRequest.getUsername()).isPresent()) {
            throw UserException.CODE.USERNAME_ALREADY_PRESENT.get();
        }
        if (userRepository.findByEmail(createUserRequest.getEmail()).isPresent()) {
            throw UserException.CODE.EMAIL_ALREADY_PRESENT.get();
        }

        var univ = createUserRequest.getUniversityId() == null
                ? null
                : universityService.findUnivById(createUserRequest.getUniversityId());
        var group = createUserRequest.getGroupId() == null
                ? null
                : groupService.findGroupById(createUserRequest.getGroupId());
        var faculty = createUserRequest.getFacultyId() == null
                ? null
                : facultyService.findFacultyById(createUserRequest.getFacultyId());

        String password = passwordEncoder.encode(createUserRequest.getPassword());

        User user = userMapper.toEntity(createUserRequest, univ, group, faculty, password);
        userRepository.save(user);
    }

    @Override
    public CreateUserResponse showCreateUser() {
        return null;
    }

    @Override
    public void updateUser(UserDto userDto, Long id) {

    }

    @Override
    public void deleteUser(Long id) {

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
}
