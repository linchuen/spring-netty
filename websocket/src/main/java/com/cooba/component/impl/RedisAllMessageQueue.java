package com.cooba.component.impl;

import com.cooba.component.MessageQueue;
import com.cooba.component.Server;
import com.cooba.component.SocketManager;
import com.cooba.constant.Topic;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisAllMessageQueue implements MessageQueue, MessageListener {
    private final SocketManager socketManager;
    private final RedisMessageListenerContainer redisMessageListenerContainer;

    @PostConstruct
    public void init() {
        subscribe(Topic.ALL);
    }

    @Override
    public void subscribe(String topic) {
        redisMessageListenerContainer.addMessageListener(this, new PatternTopic(topic));
        log.info("Subscribed to topic: {} ", topic);
    }

    @Override
    public void handleMessage(String json) {
        socketManager.allExecute((id, context) ->
                context.writeAndFlush(new TextWebSocketFrame(json)));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String body = new String(message.getBody());
        log.info("Channel {} body {} pattern {} ", channel, body, new String(pattern));

        handleMessage(body);
    }
}
