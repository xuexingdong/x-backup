package com.xxd.xpay.domain.valueobject;

import lombok.Data;

@Data
public abstract class BaseContext {
    private String appId;
    private String mchId;
    private String outTradeNo;
    private String notifyUrl;
}
