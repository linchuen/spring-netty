package com.cooba.exception;

import com.cooba.enums.ErrorType;

public class WebsocketConnectionException extends BaseException{
    public WebsocketConnectionException() {
        super(ErrorType.WEBSOCKET_CONNECTION);
    }
}
