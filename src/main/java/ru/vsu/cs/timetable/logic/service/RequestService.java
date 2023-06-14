package ru.vsu.cs.timetable.logic.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.vsu.cs.timetable.model.dto.univ_requests.*;

import java.util.List;

@Validated
public interface RequestService {

    void sendRequest(@NotNull @Valid SendRequest sendRequest,
                     @NotNull String username);

    ShowSendRequest showSendRequest(@NotNull String username);

    void moveClass(@NotNull @Valid MoveClassRequest moveClassRequest,
                   @NotNull String username);

    MoveClassResponse showMoveClass(@NotNull String username);

    List<RequestDto> getAllRequests(@NotNull String username);
}
