package com.cooba.enums;

import lombok.Getter;

@Getter
public enum SendMode {
    IN_ROOM("inRoom"),
    IGNORE("ignore"),
    ;

    private final String mode;

    SendMode(String mode) {
        this.mode = mode;
    }
}
