package com.xuexingdong.x.exception.common;

import com.xuexingdong.x.exception.BusinessException;

public class WrongPasswordException extends BusinessException {
    private int code = 5001003;
    private String message = "密码错误";
}
