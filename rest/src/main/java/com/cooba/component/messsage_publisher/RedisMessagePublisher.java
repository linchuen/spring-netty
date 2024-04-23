package com.cooba.component.messsage_publisher;


import com.cooba.constant.Topic;
import com.cooba.dto.MqMessage;
import com.cooba.enums.MessageType;
import com.cooba.enums.RedisKey;
import com.cooba.enums.SendMode;
import com.cooba.exception.WebsocketConnectionException;
import com.cooba.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisMessagePublisher implements MessagePublisher {
    private final RedisTemplate<String, String> redisTemplate;
    private final JsonUtil jsonUtil;

    @Value("${send.mode}")
    private String sendMode;

    @Override
    @Async("MessageExecutor")
    public void sendMessage(MqMessage mqMessage) {
        if (!checkInRoomMode(mqMessage)) return;

        String topic = (String) redisTemplate.opsForHash().get(RedisKey.CONNECTION.name(), mqMessage.getUserId());
        if (topic == null) {
            throw new WebsocketConnectionException();
        }

        String json = jsonUtil.toJson(mqMessage);
        redisTemplate.convertAndSend(topic, json);
    }

    private boolean checkInRoomMode(MqMessage mqMessage) {
        if (!sendMode.equalsIgnoreCase(SendMode.IN_ROOM.getMode())) return true;

        String roomId = (String) redisTemplate.opsForHash().get(RedisKey.USER_ROOM.name(), mqMessage.getUserId());
        return roomId != null && mqMessage.getRoomId().equals(Long.parseLong(roomId));
    }

    @Override
    @Async("MessageExecutor")
    public void sendMessageToAll(Long roomId, MessageType type, String message) {
        MqMessage mqMessage = MqMessage.builder()
                .roomId(roomId)
                .type(type)
                .message(message)
                .build();

        String json = jsonUtil.toJson(mqMessage);
        redisTemplate.convertAndSend(Topic.ALL, json);
    }
}
