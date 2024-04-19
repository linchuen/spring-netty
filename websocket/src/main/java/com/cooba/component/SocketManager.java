package com.cooba.component;

import io.netty.channel.ChannelHandlerContext;

public interface SocketManager {
    void cacheSocket(String id, ChannelHandlerContext context);

    void registerSocketInfo(String id, String ipAddress, int port);

    void removeSocket(String id);

    void sendMessageToAll(String message);
}
