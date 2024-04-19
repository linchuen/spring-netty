package com.cooba.task;

import com.cooba.component.SocketManager;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FixTimeTask {
    private final SocketManager socketManager;

    @Scheduled(initialDelay = 5000, fixedRate = 3000)
    public void sendMessage() {
        socketManager.allExecute((id, context) -> {
            String message = "Hello";
            context.channel().writeAndFlush(new TextWebSocketFrame(id + message));
        });
    }
}
