package com.cooba.enums;

import lombok.Getter;

@Getter
public enum ErrorType {
    VALUE_NOT_EXIST("%s value is not exist");

    ErrorType(String message) {
        this.message = message;
    }

    private final String message;

    public String getMessage(Object... objects) {
        return String.format(message,objects);
    }
}
