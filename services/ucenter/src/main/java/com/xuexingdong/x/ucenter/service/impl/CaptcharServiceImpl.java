package com.xuexingdong.x.ucenter.service.impl;

import com.xuexingdong.x.ucenter.service.CaptchaService;
import org.apache.commons.lang3.RandomStringUtils;

public class CaptcharServiceImpl implements CaptchaService {

    @Override
    public String generate() {
        return RandomStringUtils.randomNumeric(5);
    }

    @Override
    public boolean validate() {
        return false;
    }
}
