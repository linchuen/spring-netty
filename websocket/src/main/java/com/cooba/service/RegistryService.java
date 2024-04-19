package com.cooba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
@RequiredArgsConstructor
public class RegistryService {
    private final RedisTemplate<String, String> redisTemplate;
    private String hostAddress;
    @Value("${server.port}")
    private String port;

    @PostConstruct
    public void init() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            hostAddress = localHost.getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public void registryUserConnectInfo(String userId) {
        String serverInfo = hostAddress + ":" + port;
        redisTemplate.opsForHash().put("connection", userId, serverInfo);
    }
}
