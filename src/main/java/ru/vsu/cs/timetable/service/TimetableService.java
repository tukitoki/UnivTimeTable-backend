package ru.vsu.cs.timetable.service;

import jakarta.validation.constraints.NotNull;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.validation.annotation.Validated;
import ru.vsu.cs.timetable.dto.TimetableResponse;

import java.util.concurrent.CompletableFuture;

@Validated
public interface TimetableService {

    TimetableResponse getTimetable(@NotNull String username);

    Workbook downloadTimetable(@NotNull String username);

    CompletableFuture<Void> makeTimetable(@NotNull String username);
}
