package com.xxd.xpay.domain.exception;

public class XPayException extends RuntimeException {
    private final String errMsg;

    public XPayException(String errMsg) {
        this.errMsg = errMsg;
    }
}
