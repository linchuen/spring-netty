package com.cooba.controller;

import com.cooba.exception.BaseException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;
import java.util.StringJoiner;


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
