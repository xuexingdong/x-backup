package com.xuexingdong.x.auth.exception;

public class ClientException extends RuntimeException {

    private int code;

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
