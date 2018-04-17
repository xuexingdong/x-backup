package com.xuexingdong.x.ucenter.service;

public interface CaptchaService {
    String generate();

    boolean validate();
}
