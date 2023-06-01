package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.timetable.controller.api.AudienceApi;
import ru.vsu.cs.timetable.dto.audience.CreateAudienceRequest;
import ru.vsu.cs.timetable.service.AudienceService;

import java.util.List;

@RequiredArgsConstructor
@PreAuthorize("hasAuthority('CREATE_AUDIENCE_AUTHORITY')")
@RestController
public class AudienceController implements AudienceApi {

    private final AudienceService audienceService;

    @Override
    @PostMapping("/university/{univId}/faculty/{facultyId}/audience/create")
    public ResponseEntity<Void> createAudience(@RequestBody CreateAudienceRequest createAudienceRequest,
                                         @PathVariable Long univId,
                                         @PathVariable Long facultyId) {
        audienceService.createAudience(createAudienceRequest, univId, facultyId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    @GetMapping("/audience/equipments")
    public ResponseEntity<List<String>> getAvailableEquipments() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(audienceService.getAvailableEquipments());
    }
}
