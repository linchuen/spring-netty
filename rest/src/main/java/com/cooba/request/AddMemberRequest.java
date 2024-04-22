package com.cooba.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMemberRequest {
    private Long userId;
    private Long roomId;
}
