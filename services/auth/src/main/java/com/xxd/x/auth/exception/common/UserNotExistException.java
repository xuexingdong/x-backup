package com.xxd.x.auth.exception.common;


import com.xxd.x.auth.exception.BusinessException;

public final class UserNotExistException extends BusinessException {
    private int code = 5001002;
    private String message = "用户不存在";
}
