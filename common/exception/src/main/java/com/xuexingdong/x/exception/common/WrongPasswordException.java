package com.xxd.x.exception.common;

import com.xxd.x.exception.BusinessException;

public class WrongPasswordException extends BusinessException {
    private int code = 5001003;
    private String message = "密码错误";
}
