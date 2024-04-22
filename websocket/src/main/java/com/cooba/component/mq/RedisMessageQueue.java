package com.cooba.component.mq;

import com.cooba.component.mq.MessageQueue;
import com.cooba.component.server.Server;
import com.cooba.component.socketmanger.SocketManager;
import com.cooba.constant.Topic;
import com.cooba.dto.MessageDto;
import com.cooba.util.JsonUtil;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisMessageQueue implements MessageQueue, MessageListener {
    private final Server server;
    private final SocketManager socketManager;
    private final JsonUtil jsonUtil;
    private final RedisMessageListenerContainer redisMessageListenerContainer;

    @PostConstruct
    public void init() {
        String topic = Topic.server(server.getHostAddress(), server.getPort());
        subscribe(topic);
    }

    @Override
    public void subscribe(String topic) {
        redisMessageListenerContainer.addMessageListener(this, new PatternTopic(topic));
        log.info("Subscribed to topic: {} ", topic);
    }

    @Override
    public void handleMessage(String json) {
        MessageDto message = jsonUtil.fromJson(json, MessageDto.class);
        String id = message.getId();
        socketManager.execute(id, context ->
                context.writeAndFlush(new TextWebSocketFrame(json))
        );
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String body = new String(message.getBody());
        log.info("Channel {} body {} pattern {} ", channel, body, new String(pattern));

        handleMessage(body);
    }
}
