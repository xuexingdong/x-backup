package com.xuexingdong.x.ucenter.exception;

public class ClientException extends RuntimeException {

    private int code = 400;

    public ClientException() {
    }

    public ClientException(String message) {
        super(message);
    }

    public ClientException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
