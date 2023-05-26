package ru.vsu.cs.timetable.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vsu.cs.timetable.exception.TimetableException;
import ru.vsu.cs.timetable.exception.message.ErrorMessage;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
public class TimetableExceptionHandler {

    @ExceptionHandler(TimetableException.class)
    public ResponseEntity<ErrorMessage> handleAuthException(TimetableException ex) {
        TimetableException.CODE code = ex.getCode();
        HttpStatus status = switch (code) {
            case ADMIN_CANT_ACCESS, TIMETABLE_CANT_BE_GENERATED,
                    TIMETABLE_WAS_NOT_MADE, TIMETABLE_WAS_ALREADY_MADE -> BAD_REQUEST;
        };

        String codeStr = code.toString();

        log.error(codeStr, ex);

        return ResponseEntity
                .status(status)
                .body(new ErrorMessage(codeStr, ex.getMessage()));
    }
}
