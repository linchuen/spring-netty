package com.cooba.component.socketmanger;

import com.cooba.component.server.Server;

import com.cooba.constant.Topic;
import com.cooba.enums.RedisKey;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserSocketManager implements SocketManager {
    private final Map<String, Channel> idChannelMap = new ConcurrentHashMap<>();
    private final RedisTemplate<String, String> redisTemplate;
    private final Server server;

    @Override
    public void cacheSocket(String id, Channel channel) {
        boolean added = idChannelMap.putIfAbsent(id, channel) == null;
        if (added) {
            log.info("cache socket id: {} channel id: {}", id, channel.id());
            channel.closeFuture().addListener((ChannelFutureListener) future -> removeSocket(id));

            registerSocketInfo(id, server.getHostAddress(), server.getPort());
        }
    }

    @Override
    public void registerSocketInfo(String id, String ipAddress, int port) {
        String topic = Topic.server(server.getHostAddress(), server.getPort());
        redisTemplate.opsForHash().put(RedisKey.CONNECTION.name(), id, topic);
    }

    @Override
    public void removeSocket(String id) {
        idChannelMap.remove(id);
    }

    @Override
    public void allExecute(BiConsumer<String, Channel> consumer) {
        idChannelMap.forEach(consumer);
    }

    @Override
    public void execute(String id, Consumer<Channel> consumer) {
        Channel channel = idChannelMap.get(id);
        if (id == null) return;

        consumer.accept(channel);
    }
}
