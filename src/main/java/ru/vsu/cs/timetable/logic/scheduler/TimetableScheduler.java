package ru.vsu.cs.timetable.logic.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.timetable.logic.service.MailService;
import ru.vsu.cs.timetable.logic.service.TimetableService;
import ru.vsu.cs.timetable.repository.ClassRepository;
import ru.vsu.cs.timetable.repository.UserRepository;

import static ru.vsu.cs.timetable.model.entity.enums.UserRole.*;

@RequiredArgsConstructor
@Slf4j
@Component
public class TimetableScheduler {

    private final MailService mailService;
    private final TimetableService timetableService;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;

    @Async
    @Transactional
    @Scheduled(cron = "0 19 0 * * SUN", zone = "Europe/Moscow")
    public void timetableNotification() {
        for (var user : userRepository.findAll()) {
            if (user.getRole() == ADMIN) {
                continue;
            }
            if (user.getRole() == LECTURER && classRepository.findAllByLecturer(user).isEmpty()) {
                continue;
            }
            if (user.getRole() == HEADMAN) {
                if (user.getGroup() == null) {
                    continue;
                }
                if (user.getGroup().getClasses().isEmpty()) {
                    continue;
                }
            }
            var excel = timetableService.downloadTimetable(user.getUsername());
            mailService.sendExcelTimetableMail(user.getEmail(), excel);
        }
    }
}
