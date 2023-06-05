package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.timetable.controller.api.AuthApi;
import ru.vsu.cs.timetable.model.dto.user.JwtDto;
import ru.vsu.cs.timetable.model.dto.user.UserLoginDto;
import ru.vsu.cs.timetable.logic.service.AuthService;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<JwtDto> loginUser(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.loginUser(userLoginDto));
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<JwtDto> refreshToken(@RequestBody String refreshToken) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.refreshToken(refreshToken));
    }
}
