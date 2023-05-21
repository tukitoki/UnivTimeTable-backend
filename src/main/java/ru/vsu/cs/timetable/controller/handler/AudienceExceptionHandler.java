package ru.vsu.cs.timetable.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vsu.cs.timetable.exception.AudienceException;
import ru.vsu.cs.timetable.exception.message.ErrorMessage;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
public class AudienceExceptionHandler {

    @ExceptionHandler(AudienceException.class)
    public ResponseEntity<ErrorMessage> handleAuthException(AudienceException ex) {
        AudienceException.CODE code = ex.getCode();
        HttpStatus status = switch (code) {
            case AUDIENCE_ALREADY_EXIST -> BAD_REQUEST;
        };

        String codeStr = code.toString();

        log.error(codeStr, ex);

        return ResponseEntity
                .status(status)
                .body(new ErrorMessage(codeStr, ex.getMessage()));
    }
}
