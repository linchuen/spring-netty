package com.cooba.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SpeakRequest {
    private Long userId;
    private String message;
}
