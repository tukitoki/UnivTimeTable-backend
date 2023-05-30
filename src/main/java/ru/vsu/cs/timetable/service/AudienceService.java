package ru.vsu.cs.timetable.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.vsu.cs.timetable.dto.audience.CreateAudienceRequest;
import ru.vsu.cs.timetable.dto.audience.CreateAudienceResponse;
import ru.vsu.cs.timetable.dto.week_time.DayTimes;
import ru.vsu.cs.timetable.entity.Audience;
import ru.vsu.cs.timetable.entity.Faculty;

import java.util.List;
import java.util.Map;

@Validated
public interface AudienceService {

    void createAudience(@NotNull @Valid CreateAudienceRequest createAudienceRequest,
                        @NotNull Long univId, @NotNull Long facultyId);

    Audience findAudienceByNumberAndFaculty(Integer audienceNumber, Faculty faculty);

    CreateAudienceResponse showCreateAudience();

    Map<Audience, List<DayTimes>> getFreeAudienceByFaculty(@NotNull Faculty faculty);
}
