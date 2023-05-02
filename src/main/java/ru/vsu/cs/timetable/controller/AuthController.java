package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.timetable.controller.api.AuthApi;
import ru.vsu.cs.timetable.dto.user.JwtDto;
import ru.vsu.cs.timetable.dto.user.UserLoginDto;
import ru.vsu.cs.timetable.service.AuthService;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    @PostMapping("/login")
    public JwtDto loginUser(@RequestBody UserLoginDto userLoginDto) {
        return authService.loginUser(userLoginDto);
    }

    @Override
    @PostMapping("/refresh")
    public JwtDto refreshToken(@RequestBody String refreshToken) {
        return authService.refreshToken(refreshToken);
    }
}
