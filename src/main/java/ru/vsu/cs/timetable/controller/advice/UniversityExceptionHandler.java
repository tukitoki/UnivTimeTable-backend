package ru.vsu.cs.timetable.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vsu.cs.timetable.exception.UniversityException;
import ru.vsu.cs.timetable.exception.message.ErrorMessage;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class UniversityExceptionHandler {

    @ExceptionHandler(UniversityException.class)
    public ResponseEntity<ErrorMessage> handleUniversityException(UniversityException ex) {
        UniversityException.CODE code = ex.getCode();
        HttpStatus status = switch (code) {
            case ID_NOT_FOUND -> NOT_FOUND;
            case UNIVERSITY_NAME_NOT_FOUND -> BAD_REQUEST;
        };

        String codeStr = code.toString();

        log.error(codeStr, ex);

        return ResponseEntity
                .status(status)
                .body(new ErrorMessage(codeStr, ex.getMessage()));
    }
}
