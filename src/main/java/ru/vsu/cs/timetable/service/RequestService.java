package ru.vsu.cs.timetable.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.vsu.cs.timetable.dto.univ_requests.MoveClassRequest;
import ru.vsu.cs.timetable.dto.univ_requests.MoveClassResponse;
import ru.vsu.cs.timetable.dto.univ_requests.SendRequestDto;
import ru.vsu.cs.timetable.dto.univ_requests.ShowSendRequestDto;

@Validated
public interface RequestService {

    void sendRequest(@NotNull @Valid SendRequestDto sendRequestDto,
                     @NotNull String username);

    ShowSendRequestDto showSendRequest(String username);

    void moveClass(@NotNull @Valid MoveClassRequest moveClassRequest,
                   @NotNull String username);

    MoveClassResponse showMoveClass(String username);
}
