package com.xxd.x.auth.exception.common;

import com.xxd.x.auth.exception.BusinessException;

public class WrongPasswordException extends BusinessException {
    private int code = 5001003;
    private String message = "密码错误";
}
