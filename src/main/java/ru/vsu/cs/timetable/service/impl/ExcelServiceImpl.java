package ru.vsu.cs.timetable.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ru.vsu.cs.timetable.entity.Class;
import ru.vsu.cs.timetable.entity.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.entity.enums.WeekType;
import ru.vsu.cs.timetable.service.ExcelService;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.vsu.cs.timetable.utils.TimeUtils.ACADEMICAL_PAIR_MINUTES;
import static ru.vsu.cs.timetable.utils.TimeUtils.getPossibleClassTimes;

@RequiredArgsConstructor
@Service
public class ExcelServiceImpl implements ExcelService {

    private final static int FIRST_COLUMN_WIDTH = 6000;
    private final static int SECOND_COLUMN_WIDTH = 15000;
    private final static short HEADER_ROW_FONT_SIZE = 18;
    private final static short ROW_FONT_SIZE = 14;

    @Override
    public Workbook getExcelTimetable(Map<DayOfWeekEnum, List<Class>> timetable) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Timetable");
        sheet.setColumnWidth(0, FIRST_COLUMN_WIDTH);
        sheet.setColumnWidth(1, SECOND_COLUMN_WIDTH);

        int rowIndex = 0;
        for (var dayOfWeek : DayOfWeekEnum.values()) {
            Row header = sheet.createRow(rowIndex);
            header.setHeight((short) 1000);

            CellStyle headerStyle = getHeaderRowStyle(workbook);

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
                                + startTime.plusMinutes(ACADEMICAL_PAIR_MINUTES).toString()
                );
                timeCell.setCellStyle(getRowStyle(workbook));

                timeCell = row.createCell(1);
                var optClass = getByWeekTypeAndDayAndStartTime(timetable, WeekType.NUMERATOR, dayOfWeek, startTime);
                if (optClass.isEmpty()) {
                    timeCell.setCellValue("");
                } else {
                    timeCell.setCellValue(optClass.get().toString());
                }
                timeCell.setCellStyle(getRowStyle(workbook));

                rowIndex++;
                row = sheet.createRow(rowIndex);

                timeCell = row.createCell(1);
                optClass = getByWeekTypeAndDayAndStartTime(timetable, WeekType.DENOMINATOR, dayOfWeek, startTime);
                if (optClass.isEmpty()) {
                    timeCell.setCellValue("");
                } else {
                    timeCell.setCellValue(optClass.get().toString());
                }
                timeCell.setCellStyle(getRowStyle(workbook));

                sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex, 0, 0));
            }
            rowIndex++;
        }

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

        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints(ROW_FONT_SIZE);
        headerStyle.setFont(font);

        return headerStyle;
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
