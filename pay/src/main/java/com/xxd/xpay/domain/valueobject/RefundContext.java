package com.xxd.xpay.domain.valueobject;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RefundContext extends BaseContext {
    private String outRefundNo;
    private LocalDateTime tradeTime;
    private BigDecimal refundAmount;
    /**
     * 退款原因
     */
    private String refundReason;
    private String attach;
}
