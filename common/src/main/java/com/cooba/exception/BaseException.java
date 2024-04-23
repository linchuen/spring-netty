package com.cooba.exception;

import com.cooba.enums.ErrorType;

public abstract class BaseException extends RuntimeException {
    private final ErrorType errorType;

    protected BaseException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public String getType() {
        return this.errorType.name();
    }

    public String getErrorMessage(Object... objects) {
        return this.errorType.getMessage(objects);
    }

    public String getErrorMessage() {
        return this.errorType.getMessage();
    }

}
