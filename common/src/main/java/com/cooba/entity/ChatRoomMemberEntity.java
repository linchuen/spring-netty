package com.cooba.entity;

import lombok.Data;

@Data
public class ChatRoomMemberEntity {
    private Long id;
    private Long roomId;
    private Long userId;
}
