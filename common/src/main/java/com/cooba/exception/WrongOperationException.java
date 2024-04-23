package com.cooba.exception;

import com.cooba.enums.ErrorType;

public class WrongOperationException extends BaseException{
    private final String field;

    public WrongOperationException(String field) {
        super(ErrorType.WRONG_OPERATION);
        this.field = field;
    }

    public String getErrorMessage() {
        return super.getErrorMessage(field);
    }
}
