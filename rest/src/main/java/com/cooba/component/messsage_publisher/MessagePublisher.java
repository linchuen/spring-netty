package com.cooba.component.messsage_publisher;

import com.cooba.enums.MessageTypeEnum;

public interface MessagePublisher {
    void sendMessage(String id, MessageTypeEnum type, String message);

    void sendMessageToAll(MessageTypeEnum type, String message);
}
