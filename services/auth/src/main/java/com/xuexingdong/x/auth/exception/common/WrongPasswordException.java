package com.xuexingdong.x.auth.exception.common;

import com.xuexingdong.x.auth.exception.BusinessException;

public class WrongPasswordException extends BusinessException {
    private int code = 5001003;
    private String message = "密码错误";
}
