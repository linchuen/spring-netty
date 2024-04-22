package com.cooba.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateRoomRequest {
    private Long userId;
    private String name;
}
