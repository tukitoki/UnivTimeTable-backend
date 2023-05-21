package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.timetable.controller.api.AudienceApi;
import ru.vsu.cs.timetable.dto.audience.CreateAudienceRequest;
import ru.vsu.cs.timetable.dto.audience.CreateAudienceResponse;
import ru.vsu.cs.timetable.service.AudienceService;

@RequiredArgsConstructor
@PreAuthorize("hasAuthority('CREATE_AUDIENCE_AUTHORITY')")
@RestController
public class AudienceController implements AudienceApi {

    private final AudienceService audienceService;

    @Override
    @PostMapping("/university/{univId}/faculty/{facultyId}/audience/create")
    public void createAudience(@RequestBody CreateAudienceRequest createAudienceRequest,
                               @PathVariable Long univId,
                               @PathVariable Long facultyId) {
        audienceService.createAudience(createAudienceRequest, univId, facultyId);
    }

    @Override
    @GetMapping("/audience/create")
    public CreateAudienceResponse showCreateAudience() {
        return audienceService.showCreateAudience();
    }
}
