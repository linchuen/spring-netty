package com.cooba.service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class ChannelService {
    private final Map<String, ChannelHandlerContext> channelMap = new ConcurrentHashMap<>();

    public void cacheChannel(String id, ChannelHandlerContext context) {
        log.info("cache channel playerId: {} channel id: {}", id, context.channel().id());
        channelMap.put(id, context);
    }

    public void sendMessageToAll(String message) {
        channelMap.forEach((id, context) -> {
            context.channel().writeAndFlush(new TextWebSocketFrame(id + message));
        });
    }
}
