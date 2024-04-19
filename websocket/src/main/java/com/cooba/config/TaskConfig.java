package com.cooba.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@EnableScheduling
@Configuration
public class TaskConfig {
    @Bean
    public ScheduledExecutorService taskScheduler() {
        return Executors.newScheduledThreadPool(10);
    }
}
