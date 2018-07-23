package com.xuexingdong.x.backend.exception.common;

import com.xuexingdong.x.backend.exception.BusinessException;

public final class UserAlreadyExistsException extends BusinessException {
    private int code = 5001001;
    private String message = "用户已存在";

}
