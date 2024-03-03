package com.xxd.x.exception.common;


import com.xxd.x.exception.BusinessException;

public final class UserAlreadyExistsException extends BusinessException {
    private int code = 5001001;
    private String message = "用户已存在";

}
