package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.timetable.controller.api.RequestApi;
import ru.vsu.cs.timetable.logic.service.RequestService;
import ru.vsu.cs.timetable.model.dto.univ_requests.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/request")
@RestController
public class RequestController implements RequestApi {


    private final RequestService requestService;

    @Override
    @PreAuthorize("hasAuthority('SEND_REQUEST_AUTHORITY')")
    @PostMapping("/send")
    public ResponseEntity<Void> sendRequest(@RequestBody SendRequest sendRequest,
                                            Authentication authentication) {
        String username = authentication.getName();
        requestService.sendRequest(sendRequest, username);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    @PreAuthorize("hasAuthority('SEND_REQUEST_AUTHORITY')")
    @GetMapping("/send")
    public ResponseEntity<ShowSendRequest> sendRequestInfo(Authentication authentication) {
        String username = authentication.getName();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requestService.showSendRequest(username));
    }

    @Override
    @PreAuthorize("hasAuthority('MOVE_CLASS_AUTHORITY')")
    @PostMapping("/move-class")
    public ResponseEntity<Void> moveClass(@RequestBody MoveClassRequest moveClassRequest,
                                          Authentication authentication) {
        String username = authentication.getName();
        requestService.moveClass(moveClassRequest, username);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    @PreAuthorize("hasAuthority('MOVE_CLASS_AUTHORITY')")
    @GetMapping("/move-class")
    public ResponseEntity<MoveClassResponse> moveClassInfo(Authentication authentication) {
        String username = authentication.getName();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requestService.showMoveClass(username));
    }

    @Override
    @PreAuthorize("hasAuthority('MAKE_TIMETABLE_AUTHORITY')")
    @GetMapping("/all")
    public ResponseEntity<List<RequestDto>> getFacultyRequests(Authentication authentication) {
        String username = authentication.getName();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requestService.getAllRequests(username));
    }
}
