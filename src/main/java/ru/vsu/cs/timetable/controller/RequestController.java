package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.timetable.controller.api.RequestApi;
import ru.vsu.cs.timetable.dto.univ_requests.MoveClassRequest;
import ru.vsu.cs.timetable.dto.univ_requests.MoveClassResponse;
import ru.vsu.cs.timetable.dto.univ_requests.SendRequestDto;
import ru.vsu.cs.timetable.dto.univ_requests.ShowSendRequestDto;

@RequiredArgsConstructor
@RequestMapping("/request")
@RestController
public class RequestController implements RequestApi {

    @Override
    @PostMapping("/send")
    public void sendRequest(SendRequestDto sendRequestDto, Authentication authentication) {

    }

    @Override
    @GetMapping("/send")
    public ShowSendRequestDto showSendRequest() {
        return null;
    }

    @Override
    @PostMapping("/move-class")
    public void moveClass(MoveClassRequest moveClassRequest, Authentication authentication) {

    }

    @Override
    @GetMapping("/move-class")
    public MoveClassResponse showMoveClass() {
        return null;
    }
}
