package ru.vsu.cs.timetable.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vsu.cs.timetable.exception.message.ErrorMessage;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class AccessDeniedExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handleAuthException(AccessDeniedException ex) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String message = ex.getMessage() + " to user with username: %s"
                .formatted(username);

        log.error(ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorMessage(ex.getMessage(), message));
    }
}
