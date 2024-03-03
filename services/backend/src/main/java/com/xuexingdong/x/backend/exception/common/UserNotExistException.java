package com.xxd.x.backend.exception.common;

import com.xxd.x.backend.exception.BusinessException;

public final class UserNotExistException extends BusinessException {
    private int code = 5001002;
    private String message = "用户不存在";
}
