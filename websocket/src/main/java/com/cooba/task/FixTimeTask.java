package com.cooba.task;

import com.cooba.service.ChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FixTimeTask {
    private final ChannelService channelService;

    @Scheduled(initialDelay = 5000, fixedRate = 3000)
    public void sendMessage() {
        log.info("start send message to all users");
        channelService.sendMessageToAll("Hello");
    }
}
