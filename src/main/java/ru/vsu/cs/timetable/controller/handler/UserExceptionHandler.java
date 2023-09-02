package ru.vsu.cs.timetable.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vsu.cs.timetable.exception.UserException;
import ru.vsu.cs.timetable.exception.message.ErrorMessage;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorMessage> handleUserException(UserException ex) {
        UserException.CODE code = ex.getCode();
        HttpStatus status = switch (code) {
            case USERNAME_NOT_FOUND, ID_NOT_FOUND, EMAIL_NOT_FOUND -> NOT_FOUND;
            case USERNAME_ALREADY_PRESENT, EMAIL_ALREADY_PRESENT,
                    ADMIN_CANT_HAVE_FACULTY, ADMIN_CANT_HAVE_GROUP, ADMIN_CANT_HAVE_UNIV,
                    LECTURER_SHOULD_HAVE_UNIVERSITY, LECTURER_SHOULD_HAVE_FACULTY, LECTURER_CANT_HAVE_GROUP,
                    HEADMAN_SHOULD_HAVE_FACULTY, HEADMAN_SHOULD_HAVE_GROUP, HEADMAN_SHOULD_HAVE_UNIVERSITY,
                    CANT_DELETE_YOURSELF -> BAD_REQUEST;
        };

        String codeStr = code.toString();

        log.error(codeStr, ex);

        return ResponseEntity
                .status(status)
                .body(new ErrorMessage(codeStr, ex.getMessage()));
    }
}
