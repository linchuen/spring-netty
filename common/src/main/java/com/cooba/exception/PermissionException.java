package com.cooba.exception;

import com.cooba.enums.ErrorType;

public class PermissionException extends BaseException{
    public PermissionException() {
        super(ErrorType.PERMISSION_DENIED);
    }
}
