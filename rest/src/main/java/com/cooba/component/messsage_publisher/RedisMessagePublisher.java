package com.cooba.component.messsage_publisher;


import com.cooba.constant.Topic;
import com.cooba.dto.MqMessage;
import com.cooba.enums.MessageTypeEnum;
import com.cooba.enums.RedisKey;
import com.cooba.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisMessagePublisher implements MessagePublisher {
    private final RedisTemplate<String, String> redisTemplate;
    private final JsonUtil jsonUtil;

    @Override
    public void sendMessage(String id, MessageTypeEnum type, String message) {
        String topic = (String) redisTemplate.opsForHash().get(RedisKey.CONNECTION.name(), id);
        if (topic == null) {
            throw new RuntimeException();
        }

        MqMessage mqMessage = new MqMessage();
        mqMessage.setId(id);
        mqMessage.setType(type);
        mqMessage.setMessage(message);

        String json = jsonUtil.toJson(mqMessage);
        redisTemplate.convertAndSend(topic, json);
    }

    @Override
    public void sendMessageToAll(MessageTypeEnum type, String message) {
        MqMessage mqMessage = new MqMessage();
        mqMessage.setType(type);
        mqMessage.setMessage(message);

        String json = jsonUtil.toJson(mqMessage);
        redisTemplate.convertAndSend(Topic.ALL, json);
    }
}
