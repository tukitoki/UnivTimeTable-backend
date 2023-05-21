package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.timetable.controller.api.RequestApi;
import ru.vsu.cs.timetable.dto.univ_requests.MoveClassRequest;
import ru.vsu.cs.timetable.dto.univ_requests.MoveClassResponse;
import ru.vsu.cs.timetable.dto.univ_requests.SendRequestDto;
import ru.vsu.cs.timetable.dto.univ_requests.ShowSendRequestDto;
import ru.vsu.cs.timetable.service.RequestService;

@RequiredArgsConstructor
@RequestMapping("/request")
@RestController
public class RequestController implements RequestApi {


    private final RequestService requestService;

    @Override
    @PreAuthorize("hasAuthority('SEND_REQUEST_AUTHORITY')")
    @PostMapping("/send")
    public void sendRequest(@RequestBody SendRequestDto sendRequestDto,
                            Authentication authentication) {
        String username = authentication.getName();
        requestService.sendRequest(sendRequestDto, username);
    }

    @Override
    @PreAuthorize("hasAuthority('SEND_REQUEST_AUTHORITY')")
    @GetMapping("/send")
    public ShowSendRequestDto showSendRequest(Authentication authentication) {
        String username = authentication.getName();
        return requestService.showSendRequest(username);
    }

    @Override
    @PreAuthorize("hasAuthority('MOVE_CLASS_AUTHORITY')")
    @PostMapping("/move-class")
    public void moveClass(@RequestBody MoveClassRequest moveClassRequest,
                          Authentication authentication) {
        String username = authentication.getName();
        requestService.moveClass(moveClassRequest, username);
    }

    @Override
    @PreAuthorize("hasAuthority('MOVE_CLASS_AUTHORITY')")
    @GetMapping("/move-class")
    public MoveClassResponse showMoveClass(Authentication authentication) {
        String username = authentication.getName();
        return requestService.showMoveClass(username);
    }
}
