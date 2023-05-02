package ru.vsu.cs.timetable.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vsu.cs.timetable.exception.GroupException;
import ru.vsu.cs.timetable.exception.message.ErrorMessage;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class GroupExceptionHandler {

    @ExceptionHandler(GroupException.class)
    public ResponseEntity<ErrorMessage> handleGroupException(GroupException ex) {
        GroupException.CODE code = ex.getCode();
        HttpStatus status = switch (code) {
            case ID_NOT_FOUND -> NOT_FOUND;
            case GROUP_FACULTY_ALREADY_PRESENT -> BAD_REQUEST;
        };

        String codeStr = code.toString();

        log.error(codeStr, ex);

        return ResponseEntity
                .status(status)
                .body(new ErrorMessage(codeStr, ex.getMessage()));
    }
}
