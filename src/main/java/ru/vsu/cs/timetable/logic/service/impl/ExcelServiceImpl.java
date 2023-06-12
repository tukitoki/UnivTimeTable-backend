package ru.vsu.cs.timetable.logic.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ru.vsu.cs.timetable.model.entity.Class;
import ru.vsu.cs.timetable.model.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.model.enums.UserRole;
import ru.vsu.cs.timetable.model.enums.WeekType;
import ru.vsu.cs.timetable.logic.service.ExcelService;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.vsu.cs.timetable.utils.TimeUtils.calculateEndTimeByStart;
import static ru.vsu.cs.timetable.utils.TimeUtils.getPossibleClassTimes;

@RequiredArgsConstructor
@Slf4j
@Service
public class ExcelServiceImpl implements ExcelService {

    private final static int FIRST_COLUMN_WIDTH = 6000;
    private final static int SECOND_COLUMN_WIDTH = 15000;
    private final static short HEADER_ROW_FONT_SIZE = 18;
    private final static short ROW_FONT_SIZE = 14;

    @Override
    public Workbook getExcelTimetable(Map<DayOfWeekEnum, List<Class>> timetable, UserRole userRole) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Timetable");
        sheet.setColumnWidth(0, FIRST_COLUMN_WIDTH);
        sheet.setColumnWidth(1, SECOND_COLUMN_WIDTH);
        sheet.autoSizeColumn(0);

        CellStyle headerStyle = getHeaderRowStyle(workbook);
        CellStyle rowStyle = getRowStyle(workbook);

        int rowIndex = 0;
        for (var dayOfWeek : DayOfWeekEnum.values()) {
            Row header = sheet.createRow(rowIndex);
            header.setHeight((short) 1000);

            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("Время");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(1);
            headerCell.setCellValue(dayOfWeek.toString());
            headerCell.setCellStyle(headerStyle);

            for (var startTime : getPossibleClassTimes()) {
                rowIndex++;
                Row row = sheet.createRow(rowIndex);

                Cell timeCell = row.createCell(0);
                timeCell.setCellValue(
                        startTime.toString()
                                + " - "
                                + calculateEndTimeByStart(startTime).toString()
                );
                timeCell.setCellStyle(rowStyle);

                cellCreate(row, timetable, WeekType.NUMERATOR, dayOfWeek, startTime, rowStyle, userRole);

                rowIndex++;
                row = sheet.createRow(rowIndex);

                cellCreate(row, timetable, WeekType.DENOMINATOR, dayOfWeek, startTime, rowStyle, userRole);

                sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex, 0, 0));
            }
            rowIndex++;
        }

        log.info("Excel file was successful generated");

        return workbook;
    }

    private CellStyle getHeaderRowStyle(XSSFWorkbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints(HEADER_ROW_FONT_SIZE);
        font.setBold(true);
        headerStyle.setFont(font);

        return headerStyle;
    }

    private CellStyle getRowStyle(XSSFWorkbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setWrapText(true);

        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints(ROW_FONT_SIZE);
        headerStyle.setFont(font);

        return headerStyle;
    }

    private void cellCreate(Row row, Map<DayOfWeekEnum, List<Class>> timetable, WeekType weekType,
                            DayOfWeekEnum dayOfWeek, LocalTime startTime, CellStyle rowStyle, UserRole userRole) {
        Cell subjCell = row.createCell(1);
        var optClass = getByWeekTypeAndDayAndStartTime(timetable, weekType, dayOfWeek, startTime);
        if (optClass.isEmpty()) {
            subjCell.setCellValue("");
        } else {
            subjCell.setCellValue(optClass.get().toExcelFormat(userRole));
        }
        subjCell.setCellStyle(rowStyle);
    }

    private Optional<Class> getByWeekTypeAndDayAndStartTime(Map<DayOfWeekEnum, List<Class>> timetable, WeekType weekType,
                                                            DayOfWeekEnum dayOfWeek, LocalTime startTime) {
        Optional<Class> optionalClass = Optional.empty();

        for (var entry : timetable.entrySet()) {
            var dayOfWeekEnum = entry.getKey();
            if (dayOfWeekEnum == dayOfWeek) {
                var classes = entry.getValue();
                optionalClass = classes.stream()
                        .filter(aClass -> aClass.getWeekType() == weekType && aClass.getStartTime().equals(startTime))
                        .findFirst();
                break;
            }
        }

        return optionalClass;
    }
}
