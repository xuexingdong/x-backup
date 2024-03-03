package com.xxd.xpay.infrastructure.dao;

import com.xxd.xpay.domain.enums.PayOrderStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PayOrderDO {
    private Long id;
    private String mchId;
    private String paySerialNo;
    private LocalDateTime tradeTime;
    private LocalDateTime payTime;
    private String outTradeNo;
    private String outRefundNo;
    private PayOrderStatusEnum payOrderStatus;
}
