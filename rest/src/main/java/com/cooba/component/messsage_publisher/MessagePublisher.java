package com.cooba.component.messsage_publisher;

import com.cooba.dto.MqMessage;
import com.cooba.enums.MessageType;

public interface MessagePublisher {
    void sendMessage(MqMessage mqMessage);

    void sendMessageToAll(Long roomId, MessageType type, String message);
}
