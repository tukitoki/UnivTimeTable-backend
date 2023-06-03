package ru.vsu.cs.timetable.service.impl;

import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.vsu.cs.timetable.entity.Class;
import ru.vsu.cs.timetable.entity.User;
import ru.vsu.cs.timetable.service.MailService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Async
@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender emailSender;

    @Override
    public void sendClassChangeMail(User lecturer, Class fromClass, Class toClass, String toSend) {
        try {
            String messageText = "Преподователь %s перенес пару [%s] на [%s]"
                    .formatted(lecturer.getFullName(), fromClass.toStringMoveClass(), toClass.toStringMoveClass());
            sendMessage(toSend, "Перенос занятия", messageText);
        } catch (MessagingException e) {
            log.error("Error occurred", e);
        }
    }

    @Override
    public void sendTimetableWasMade(String to) {
        try {
            String messageText = "Расписание было составлено. " +
                    "Для его просмотра перейдите в приложение.";
            sendMessage(to, "Университетское расписание", messageText);
        } catch (MessagingException e) {
            log.error("Error occurred");
        }
    }

    @Override
    public void sendTimetableCantMade(String to, String summaryViolations) {
        try {
            String messageText = "Расписание не смогло составиться из-за внутренних проблем." +
                    "Перечень проблем: %s".formatted(summaryViolations) +
                    "Передайте информацию администратору";
            sendMessage(to, "Расписание не смогло составиться", messageText);
        } catch (MessagingException e) {
            log.error("Error occurred");
        }
    }

    @Override
    public void sendExcelTimetableMail(String toSend, Workbook workbook) {
        try {
            sendExcelMessage(toSend, "Получение в excel формате", workbook);
        } catch (MessagingException | IOException e) {
            log.error("Error occurred", e);
        }
    }

    private void sendExcelMessage(String toSend, String title, Workbook workbook) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toSend);
        helper.setText("Ваше расписание в excel");
        helper.setSubject(title);

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            workbook.write(bos);

            DataSource fds = new ByteArrayDataSource(bos.toByteArray(), "application/vnd.ms-excel");

            helper.addAttachment("timetable", fds);
            emailSender.send(message);

            log.info("[mail/{} , to: {}, title: {}] was sent ", message, toSend, title);
        }
    }

    private void sendMessage(String toSend, String title, String messageText) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toSend);
        helper.setSubject(title);
        helper.setText(messageText);
        emailSender.send(message);

        log.info("[mail/{} , to: {}, title: {}] was sent ", message, toSend, title);
    }
}
