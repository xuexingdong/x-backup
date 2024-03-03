package com.xxd.xpay.domain.valueobject;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PayContext extends BaseContext {
    private String notifyUrl;
    private String description;
    private LocalDateTime tradeTime;
    private BigDecimal payAmount;
    private String attach;
    private String timeExpire;

}
