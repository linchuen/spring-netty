package com.cooba.dto;

import com.cooba.enums.MessageTypeEnum;
import lombok.Data;

@Data
public class MessageDto {
    private String id;
    private MessageTypeEnum type;
    private String message;
}
