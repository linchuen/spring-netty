package com.cooba.component.impl;

import com.cooba.component.Server;
import com.cooba.component.SocketManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserSocketManager implements SocketManager {
    private final Map<String, ChannelHandlerContext> channelMap = new ConcurrentHashMap<>();
    private final RedisTemplate<String, String> redisTemplate;
    private final Server server;

    @Override
    public void cacheSocket(String id, ChannelHandlerContext context) {
        log.info("cache socket id: {} channel id: {}", id, context.channel().id());
        channelMap.put(id, context);

        registerSocketInfo(id, server.getHostAddress(), server.getPort());
    }

    @Override
    public void registerSocketInfo(String id, String ipAddress, int port) {
        String serverInfo = ipAddress + ":" + port;
        redisTemplate.opsForHash().put("connection", id, serverInfo);
    }

    @Override
    public void removeSocket(String id) {
        channelMap.remove(id);
    }

    @Override
    public void sendMessageToAll(String message) {
        channelMap.forEach((id, context) -> {
            context.channel().writeAndFlush(new TextWebSocketFrame(id + message));
        });
    }
}
