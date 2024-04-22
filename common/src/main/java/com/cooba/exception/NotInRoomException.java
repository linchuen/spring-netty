package com.cooba.exception;

import com.cooba.enums.ErrorType;

public class NotInRoomException extends BaseException{
    private final String field;

    public NotInRoomException(String field) {
        super(ErrorType.NOT_IN_ROOM);
        this.field = field;
    }

    public String getErrorMessage() {
        return super.getErrorMessage(field);
    }
}
