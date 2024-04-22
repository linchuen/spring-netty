package com.cooba.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteMemberRequest {
    private Long userId;
    private Long roomId;
}
