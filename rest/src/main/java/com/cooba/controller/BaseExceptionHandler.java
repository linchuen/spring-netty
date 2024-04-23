package com.cooba.controller;

import com.cooba.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler({BaseException.class})
    public final ResponseEntity<String> handleBaseException(BaseException exception) {
        return ResponseEntity.ok(exception.getErrorMessage());
    }

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<?> handleException(Throwable exception) {
        return ResponseEntity.internalServerError().build();
    }
}
