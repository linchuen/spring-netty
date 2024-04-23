package com.cooba.enums;

import lombok.Getter;

@Getter
public enum ErrorType {
    VALUE_NOT_EXIST("%s value is not exist"),
    NOT_IN_ROOM("%s is not in room"),
    PERMISSION_DENIED("permission denied"),
    WRONG_OPERATION("wrong operation reason:%s"),
    WEBSOCKET_CONNECTION("websocket connection")
    ;

    ErrorType(String message) {
        this.message = message;
    }

    private final String message;

    public String getMessage(Object... objects) {
        return String.format(message,objects);
    }
}
