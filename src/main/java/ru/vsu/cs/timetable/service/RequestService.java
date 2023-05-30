package ru.vsu.cs.timetable.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.vsu.cs.timetable.dto.univ_requests.MoveClassRequest;
import ru.vsu.cs.timetable.dto.univ_requests.MoveClassResponse;
import ru.vsu.cs.timetable.dto.univ_requests.SendRequest;
import ru.vsu.cs.timetable.dto.univ_requests.ShowSendRequest;

@Validated
public interface RequestService {

    void sendRequest(@NotNull @Valid SendRequest sendRequest,
                     @NotNull String username);

    ShowSendRequest showSendRequest(String username);

    void moveClass(@NotNull @Valid MoveClassRequest moveClassRequest,
                   @NotNull String username);

    MoveClassResponse showMoveClass(String username);
}
