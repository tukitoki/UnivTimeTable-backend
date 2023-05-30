package ru.vsu.cs.timetable.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import ru.vsu.cs.timetable.dto.univ_requests.MoveClassRequest;
import ru.vsu.cs.timetable.dto.univ_requests.MoveClassResponse;
import ru.vsu.cs.timetable.dto.univ_requests.SendRequest;
import ru.vsu.cs.timetable.dto.univ_requests.ShowSendRequest;

@Tag(name = "Request API", description = "API для работы с заявками преподавателей")
@SecurityRequirement(name = "bearer-key")
public interface RequestApi {

    @Operation(
            summary = "Отправка заявки на составление расписания"
    )
    void sendRequest(
            @Parameter(description = "Вся информация о заявке преподавателя")
            SendRequest sendRequest,
            @Parameter(hidden = true)
            Authentication authentication
    );

    @Operation(
            summary = "Показ информациии для страницы подачи заявки на составление"
    )
    ShowSendRequest showSendRequest(
            @Parameter(hidden = true)
            Authentication authentication
    );

    @Operation(
            summary = "Отправка заявка на перенос занятия"
    )
    void moveClass(
            @Parameter(description = "Вся информация о заявке на перенос")
            MoveClassRequest moveClassRequest,
            @Parameter(hidden = true)
            Authentication authentication
    );

    @Operation(
            summary = "Показ информациии для страницы подачи заявки на перенос"
    )
    MoveClassResponse showMoveClass(
            @Parameter(hidden = true)
            Authentication authentication
    );
}
