package ru.vsu.cs.timetable.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.vsu.cs.timetable.dto.audience.CreateAudienceRequest;
import ru.vsu.cs.timetable.dto.audience.CreateAudienceResponse;

@Validated
public interface AudienceService {

    void createAudience(@NotNull @Valid CreateAudienceRequest createAudienceRequest,
                        @NotNull Long univId, @NotNull Long facultyId);

    CreateAudienceResponse showCreateAudience();
}
