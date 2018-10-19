package com.xuexingdong.x.backend.exception.activity;

import com.xuexingdong.x.backend.exception.BusinessException;

public final class PointNotEnoughException extends BusinessException {
    private int code = 5002001;
    private String message = "积分不足";
}
