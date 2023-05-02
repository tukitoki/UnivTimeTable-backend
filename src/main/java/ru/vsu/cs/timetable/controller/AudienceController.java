package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.timetable.controller.api.AudienceApi;
import ru.vsu.cs.timetable.dto.audience.CreateAudienceRequest;
import ru.vsu.cs.timetable.dto.audience.CreateAudienceResponse;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class AudienceController implements AudienceApi {

    @Override
    @PostMapping("/audience/create")
    public void createAudience(@RequestBody CreateAudienceRequest createAudienceRequest) {

    }

    @Override
    @GetMapping("/audience/create")
    public CreateAudienceResponse showCreateAudience() {
        System.out.println("dsadas");
        return null;
    }
}
