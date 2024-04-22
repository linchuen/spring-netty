package com.cooba.component.messsage_publisher;

import com.cooba.component.messsage_publisher.MessagePublisher;
import com.cooba.constant.RedisKey;
import com.cooba.constant.Topic;
import com.cooba.dto.MessageDto;
import com.cooba.enums.MessageTypeEnum;
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
        String topic = (String) redisTemplate.opsForHash().get(RedisKey.CONNECTION, id);
        if (topic == null) {
            throw new RuntimeException();
        }

        MessageDto messageDto = new MessageDto();
        messageDto.setId(id);
        messageDto.setType(type);
        messageDto.setMessage(message);

        String json = jsonUtil.toJson(messageDto);
        redisTemplate.convertAndSend(topic, json);
    }

    @Override
    public void sendMessageToAll(MessageTypeEnum type, String message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setType(type);
        messageDto.setMessage(message);

        String json = jsonUtil.toJson(messageDto);
        redisTemplate.convertAndSend(Topic.ALL, json);
    }
}
