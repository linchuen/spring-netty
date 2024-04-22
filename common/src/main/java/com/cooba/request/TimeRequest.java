package com.cooba.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeRequest {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TimeRequest(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;

        if (startTime != null && endTime != null) {
            if (startTime.isAfter(endTime)) {
                throw new RuntimeException();
            }
        }
    }
}
