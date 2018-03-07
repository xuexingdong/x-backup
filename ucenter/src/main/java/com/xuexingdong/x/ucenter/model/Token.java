package com.xuexingdong.x.ucenter.model;

public class Token {
    private String token;
    private long expireTime;

    public String getToken() {
        return token;
    }

    public Token setToken(String token) {
        this.token = token;
        return this;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public Token setExpireTime(long expireTime) {
        this.expireTime = expireTime;
        return this;
    }
}
