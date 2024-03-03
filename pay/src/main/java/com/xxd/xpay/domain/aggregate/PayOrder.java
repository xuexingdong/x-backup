package com.xxd.xpay.domain.aggregate;

import com.xxd.x.ddd.AggregateRoot;
import com.xxd.xpay.domain.enums.OrderTypeEnum;
import com.xxd.xpay.domain.enums.PayOrderStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PayOrder extends AggregateRoot<PayOrder, Long> {
    @Override
    public boolean sameIdentityAs(PayOrder other) {
        return false;
    }

    public PayOrder() {
        super(1L);
    }

    @Override
    public Long id() {
        return null;
    }

    private Long id;
    private String mchId;
    private String paySerialNo;
    private OrderTypeEnum orderType;
    /**
     * 退订单的outTradeNo是正订单的outTradeNo
     */
    private String outTradeNo;
    private BigDecimal amount;
    private String outRefundNo;
    private PayType payTypeCode;
    private PayChannel payChannel;
    private LocalDateTime tradeTime;
    private String notifyUrl;
    private LocalDateTime payTime;
    private PayOrderStatusEnum payOrderStatus;
}
