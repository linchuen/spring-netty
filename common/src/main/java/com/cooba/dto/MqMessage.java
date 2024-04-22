package com.cooba.dto;

import com.cooba.enums.MessageType;
import lombok.Data;

@Data
public class MqMessage {
    private String id;
    private MessageType type;
    private String message;
}
