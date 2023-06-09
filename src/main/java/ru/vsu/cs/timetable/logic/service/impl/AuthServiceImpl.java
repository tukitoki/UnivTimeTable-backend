package ru.vsu.cs.timetable.logic.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.vsu.cs.timetable.model.dto.user.JwtDto;
import ru.vsu.cs.timetable.model.dto.user.UserLoginDto;
import ru.vsu.cs.timetable.model.entity.User;
import ru.vsu.cs.timetable.security.JwtTokenProvider;
import ru.vsu.cs.timetable.logic.service.AuthService;
import ru.vsu.cs.timetable.logic.service.UserService;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtDto loginUser(UserLoginDto userLoginDto) {
        User user = userService.getUserByEmail(userLoginDto.getEmail());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(), userLoginDto.getPassword()));

        log.info("user: {} was successfully logged in", user);

        return new JwtDto(jwtTokenProvider.generateAccessToken(user), jwtTokenProvider.generateRefreshToken(user));
    }

    @Override
    public JwtDto refreshToken(String refreshToken) {
        String username = jwtTokenProvider.getUsernameFromJwt(refreshToken);
        User user = userService.getUserByUsername(username);

        log.info("user: {} was successfully called refreshToken", user);

        return new JwtDto(jwtTokenProvider.generateAccessToken(user), jwtTokenProvider.generateRefreshToken(user));
    }
}
