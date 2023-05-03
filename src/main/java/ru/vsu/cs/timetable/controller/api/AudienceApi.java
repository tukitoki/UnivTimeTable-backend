package ru.vsu.cs.timetable.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.vsu.cs.timetable.dto.audience.CreateAudienceRequest;
import ru.vsu.cs.timetable.dto.audience.CreateAudienceResponse;

@Tag(name = "Audience API", description = "API для работы с аудиториями")
@SecurityRequirement(name = "bearer-key")
public interface AudienceApi {

    @Operation(
            summary = "Создание аудитрии конкретного факультета"
    )
    void createAudience(
            @Parameter(description = "Создание аудитории конкретного факультета")
            CreateAudienceRequest createAudienceRequest
    );

    @Operation(
            summary = "Показ страницы создания аудитории конкретного факультета"
    )
    CreateAudienceResponse showCreateAudience();
}
