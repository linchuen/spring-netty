package com.cooba.component.impl;

import com.cooba.component.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class NettyServer implements Server {
    private String hostAddress;
    @Value("${server.port}")
    private Integer port;

    @PostConstruct
    public void init() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            hostAddress = localHost.getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String getHostAddress() {
        return hostAddress;
    }

    @Override
    public int getPort() {
        return port;
    }
}
