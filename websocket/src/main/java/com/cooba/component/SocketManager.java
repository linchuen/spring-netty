package com.cooba.component;

import io.netty.channel.ChannelHandlerContext;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface SocketManager {
    void cacheSocket(String id, ChannelHandlerContext context);

    void registerSocketInfo(String id, String ipAddress, int port);

    void removeSocket(String id);

    void allExecute(BiConsumer<String, ChannelHandlerContext> consumer);

    void execute(String id, Consumer<ChannelHandlerContext> consumer);
}
