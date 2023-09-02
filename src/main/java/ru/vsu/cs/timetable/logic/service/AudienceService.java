package ru.vsu.cs.timetable.logic.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.vsu.cs.timetable.model.dto.audience.AudienceDto;
import ru.vsu.cs.timetable.model.dto.audience.AudienceResponse;
import ru.vsu.cs.timetable.model.dto.audience.CreateAudienceRequest;
import ru.vsu.cs.timetable.model.dto.week_time.DayTimes;
import ru.vsu.cs.timetable.model.entity.Audience;
import ru.vsu.cs.timetable.model.entity.Faculty;

import java.util.List;
import java.util.Map;

@Validated
public interface AudienceService {

    void createAudience(@NotNull @Valid CreateAudienceRequest createAudienceRequest,
                        @NotNull Long univId, @NotNull Long facultyId);

    AudienceDto getAudienceById(@NotNull Long id);

    void deleteAudience(@NotNull Long id);

    void updateAudience(@NotNull @Valid AudienceDto audienceDto, @NotNull Long id);

    List<AudienceResponse> getAudiencesByFaculty(@NotNull Long facultyId);

    Audience findAudienceByNumberAndFaculty(Integer audienceNumber, Faculty faculty);

    List<String> getAvailableEquipments();

    Map<Audience, List<DayTimes>> getFreeAudiencesByFaculty(@NotNull Faculty faculty);
}
