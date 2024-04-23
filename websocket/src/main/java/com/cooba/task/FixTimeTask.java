package com.cooba.task;

import com.cooba.component.socketmanger.SocketManager;
import com.cooba.entity.UserEntity;
import com.cooba.repository.UserRepository;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class FixTimeTask {
    private final SocketManager socketManager;
    private final UserRepository userRepository;

    @Scheduled(initialDelay = 5000)
    public void sendMessage() {
        socketManager.allExecute((id, channel) -> {
            Optional<UserEntity> user = userRepository.findById(Long.parseLong(id));
            if (user.isEmpty()) return;
            String message = "Hello" + user.get().getName();
            channel.writeAndFlush(new TextWebSocketFrame(message));
        });
    }
}
