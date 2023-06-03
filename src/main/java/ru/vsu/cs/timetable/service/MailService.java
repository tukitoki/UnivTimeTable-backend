package ru.vsu.cs.timetable.service;

import org.apache.poi.ss.usermodel.Workbook;
import ru.vsu.cs.timetable.entity.Class;
import ru.vsu.cs.timetable.entity.User;

public interface MailService {

    void sendClassChangeMail(User lecturer, Class fromClass, Class toClass, String toSend);

    void sendTimetableWasMade(String to);

    void sendTimetableCantMade(String to, String summaryViolations);

    void sendExcelTimetableMail(String toSend, Workbook workbook);
}
