package ru.vsu.cs.timetable.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.timetable.controller.api.TimetableApi;
import ru.vsu.cs.timetable.exception.message.ErrorMessage;
import ru.vsu.cs.timetable.logic.service.TimetableService;
import ru.vsu.cs.timetable.model.dto.TimetableResponse;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/schedule")
@RestController
public class TimetableController implements TimetableApi {

    private final TimetableService timetableService;

    @Override
    @PreAuthorize("hasAnyAuthority('GET_SCHEDULE')")
    @GetMapping
    public ResponseEntity<TimetableResponse> getTimetable(Authentication authentication) {
        String username = authentication.getName();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(timetableService.getTimetable(username));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('GET_SCHEDULE')")
    @GetMapping("/download")
    public ResponseEntity<?> downloadTimetable(HttpServletResponse httpServletResponse, Authentication authentication) {
        String username = authentication.getName();

        try (var workBook = timetableService.downloadTimetable(username)) {
            httpServletResponse.setContentType("application/vnd.ms-excel");
            httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=timetable.xlsx");

            workBook.write(httpServletResponse.getOutputStream());
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.name(), e.getMessage()));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    @PreAuthorize("hasAuthority('MAKE_TIMETABLE_AUTHORITY')")
    @PostMapping("/make")
    public ResponseEntity<Void> makeTimetable(Authentication authentication) {
        String username = authentication.getName();
        timetableService.makeTimetable(username);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    @PreAuthorize("hasAuthority('MAKE_TIMETABLE_AUTHORITY')")
    @PostMapping("/reset")
    public ResponseEntity<Void> resetTimetable(Authentication authentication) {
        String username = authentication.getName();
        timetableService.resetTimetable(username);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
