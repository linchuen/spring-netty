package com.cooba.exception;

import com.cooba.enums.ErrorType;

public class ValueNotExistException extends BaseException{
    private final String field;

    public ValueNotExistException(String field) {
        super(ErrorType.VALUE_NOT_EXIST);
        this.field = field;
    }

    public String getErrorMessage() {
        return super.getErrorMessage(field);
    }
}
