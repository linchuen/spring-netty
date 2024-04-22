package com.cooba.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoomEnterRequest {
    private Long userId;
    private Long roomId;
}
