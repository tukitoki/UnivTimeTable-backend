package ru.vsu.cs.timetable.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@RestControllerAdvice
public class HttpClientErrorExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> handleHttpClientErrorException(HttpClientErrorException ex) {
        log.error("Exception in the external service", ex);

        return ResponseEntity
                .status(ex.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ex.getResponseBodyAsString());
    }
}
