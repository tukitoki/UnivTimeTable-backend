package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('SEND_REQUEST_AUTHORITY')")
    @PostMapping("/send")
    public void sendRequest(SendRequestDto sendRequestDto, Authentication authentication) {

    }

    @Override
    @PreAuthorize("hasAuthority('SEND_REQUEST_AUTHORITY')")
    @GetMapping("/send")
    public ShowSendRequestDto showSendRequest() {
        return null;
    }

    @Override
    @PreAuthorize("hasAuthority('MOVE_CLASS_AUTHORITY')")
    @PostMapping("/move-class")
    public void moveClass(MoveClassRequest moveClassRequest, Authentication authentication) {

    }

    @Override
    @PreAuthorize("hasAuthority('MOVE_CLASS_AUTHORITY')")
    @GetMapping("/move-class")
    public MoveClassResponse showMoveClass() {
        return null;
    }
}
