package com.cooba.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatEntity {
    private Long id;
    private Long roomId;
    private Long userId;
    private String type;
    private String message;
    private LocalDateTime createdTime;
}
