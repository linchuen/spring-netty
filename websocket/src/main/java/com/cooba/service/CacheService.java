package com.cooba.service;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class CacheService {
    private final Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    public void cacheChannel(String id, Channel channel) {
        log.info("cache channel playerId: {} channel id: {}", id, channel.id());
        channelMap.put(id, channel);
    }
}
