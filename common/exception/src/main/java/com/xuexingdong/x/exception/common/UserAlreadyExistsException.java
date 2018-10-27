package com.xuexingdong.x.exception.common;


import com.xuexingdong.x.exception.BusinessException;

public final class UserAlreadyExistsException extends BusinessException {
    private int code = 5001001;
    private String message = "用户已存在";

}
