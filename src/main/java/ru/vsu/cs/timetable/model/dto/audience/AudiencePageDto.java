package ru.vsu.cs.timetable.model.dto.audience;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.page.PageModel;

@Getter
@Setter
@SuperBuilder
@Schema(description = "Страница с аудиториями")
public class AudiencePageDto {

    @Schema(description = "Страница с аудиториями")
    PageModel<AudienceResponse> audiencesPage;
}
