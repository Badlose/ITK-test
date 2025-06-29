package ru.ITK_test.ITK_test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.ITK_test.ITK_test.exception.ApiParentException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiParentException.class)
    public ResponseEntity<String> handleApiParentException(ApiParentException e) {
        ResponseStatus responseStatus = e.getClass().getAnnotation(ResponseStatus.class);
        log.error("[error]    Exception has occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(responseStatus.code()).body(e.getMessage());
    }

}
