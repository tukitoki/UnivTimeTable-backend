package ru.vsu.cs.timetable.service;

import org.apache.poi.ss.usermodel.Workbook;
import ru.vsu.cs.timetable.entity.Class;
import ru.vsu.cs.timetable.entity.enums.DayOfWeekEnum;

import java.util.List;
import java.util.Map;

public interface ExcelService {

    Workbook getExcelTimetable(Map<DayOfWeekEnum, List<Class>> timetable);
}
