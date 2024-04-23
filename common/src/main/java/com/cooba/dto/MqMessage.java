package com.cooba.dto;

import com.cooba.enums.MessageType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MqMessage {
    private String userId;
    private Long roomId;
    private MessageType type;
    private String message;
}
