package com.xuexingdong.x.backend.exception.common;

import com.xuexingdong.x.backend.exception.BusinessException;

public final class SecretKeyErrorException extends BusinessException {
    private int code = 5001003;
    private String message = "密钥错误";

}
