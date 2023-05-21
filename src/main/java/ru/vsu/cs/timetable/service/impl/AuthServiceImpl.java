package ru.vsu.cs.timetable.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.vsu.cs.timetable.dto.user.JwtDto;
import ru.vsu.cs.timetable.dto.user.UserLoginDto;
import ru.vsu.cs.timetable.entity.User;
import ru.vsu.cs.timetable.security.JwtTokenProvider;
import ru.vsu.cs.timetable.service.AuthService;
import ru.vsu.cs.timetable.service.UserService;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtDto loginUser(UserLoginDto userLoginDto) {
        User user = userService.getUserByUsername(userLoginDto.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLoginDto.getUsername(), userLoginDto.getPassword()));

        return new JwtDto(jwtTokenProvider.generateAccessToken(user), jwtTokenProvider.generateRefreshToken(user));
    }

    @Override
    public JwtDto refreshToken(String refreshToken) {
        String username = jwtTokenProvider.getUsernameFromJwt(refreshToken);
        User user = userService.getUserByUsername(username);

        return new JwtDto(jwtTokenProvider.generateAccessToken(user), jwtTokenProvider.generateRefreshToken(user));
    }
}
