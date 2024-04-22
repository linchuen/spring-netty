package com.cooba.entity;

import lombok.Data;

@Data
public class ChatRoomEntity {
    private Long id;
    private String name;

    public ChatRoomEntity(String name) {
        this.name = name;
    }
}
