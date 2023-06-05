package ru.vsu.cs.timetable.logic.service;

import jakarta.validation.constraints.NotNull;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.validation.annotation.Validated;
import ru.vsu.cs.timetable.model.dto.TimetableResponse;

@Validated
public interface TimetableService {

    TimetableResponse getTimetable(@NotNull String username);

    Workbook downloadTimetable(@NotNull String username);

    void makeTimetable(@NotNull String username);
}
