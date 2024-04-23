package com.cooba.component.messsage_publisher;


import com.cooba.constant.Topic;
import com.cooba.dto.MqMessage;
import com.cooba.enums.MessageType;
import com.cooba.enums.RedisKey;
import com.cooba.exception.WebsocketConnectionException;
import com.cooba.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisMessagePublisher implements MessagePublisher {
    private final RedisTemplate<String, String> redisTemplate;
    private final JsonUtil jsonUtil;

    @Override
    @Async("MessageExecutor")
    public void sendMessage(MqMessage mqMessage) {
        String topic = (String) redisTemplate.opsForHash().get(RedisKey.CONNECTION.name(), mqMessage.getUserId());
        if (topic == null) {
            throw new WebsocketConnectionException();
        }

        String json = jsonUtil.toJson(mqMessage);
        redisTemplate.convertAndSend(topic, json);
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
