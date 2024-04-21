package com.cooba.component;

import io.netty.channel.Channel;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface SocketManager {
    void cacheSocket(String id, Channel channel);

    void registerSocketInfo(String id, String ipAddress, int port);

    void removeSocket(String id);

    void allExecute(BiConsumer<String, Channel> consumer);

    void execute(String id, Consumer<Channel> consumer);
}
