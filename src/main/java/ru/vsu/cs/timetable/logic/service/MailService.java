package ru.vsu.cs.timetable.logic.service;

import org.apache.poi.ss.usermodel.Workbook;
import ru.vsu.cs.timetable.model.entity.Class;
import ru.vsu.cs.timetable.model.entity.User;

import java.util.Set;

public interface MailService {

    void sendClassChangeMail(User lecturer, Class fromClass, Class toClass, String toSend);

    void sendTimetableWasMade(String to);

    void sendAudienceWasDeleted(String to);

    void sendAudienceWasUpdated(String to, Set<String> changedEquipments, Integer changedNumber, Long changedCapacity);

    void sendTimetableCantMade(String to, String summaryViolations);

    void sendExcelTimetableMail(String toSend, Workbook workbook);
}
