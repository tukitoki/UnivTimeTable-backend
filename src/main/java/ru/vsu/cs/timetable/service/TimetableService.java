package ru.vsu.cs.timetable.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.vsu.cs.timetable.dto.TimetableResponse;

@Validated
public interface TimetableService {

    TimetableResponse getTimetable(@NotNull String username);

    void downloadTimetable(@NotNull String username);

    void makeTimetable(@NotNull String username);
}
