package com.cooba.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteRoomRequest {
    private Long userId;
    private Long roomId;
}
