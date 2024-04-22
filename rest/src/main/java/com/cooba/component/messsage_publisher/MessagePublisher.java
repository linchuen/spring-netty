package com.cooba.component.messsage_publisher;

import com.cooba.enums.MessageType;

public interface MessagePublisher {
    void sendMessage(String id, MessageType type, String message);

    void sendMessageToAll(MessageType type, String message);
}
