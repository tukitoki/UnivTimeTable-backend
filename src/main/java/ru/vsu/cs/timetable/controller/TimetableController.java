package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.timetable.controller.api.TimetableApi;
import ru.vsu.cs.timetable.dto.TimetableResponse;
import ru.vsu.cs.timetable.service.TimetableService;

@RequiredArgsConstructor
@RestController
public class TimetableController implements TimetableApi {

    private final TimetableService timetableService;

    @Override
    public TimetableResponse getTimetable(Authentication authentication) {
        String username = authentication.getName();
        return timetableService.getTimetable(username);
    }

    @Override
    public void downloadTimetable(Authentication authentication) {
        String username = authentication.getName();
    }

    @Override
    @PostMapping("/timetable/make")
    public void makeTimetable(Authentication authentication) {
        String username = authentication.getName();
        timetableService.makeTimetable(username);
    }
}
