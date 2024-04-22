package com.cooba.condition;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeCondition {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TimeCondition(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;

        if (startTime != null && endTime != null) {
            if (startTime.isAfter(endTime)) {
                throw new RuntimeException();
            }
        }
    }
}
