package com.xxd.x.auth.exception.common;


import com.xxd.x.auth.exception.BusinessException;

public final class UserAlreadyExistsException extends BusinessException {
    private int code = 5001001;
    private String message = "用户已存在";

}
