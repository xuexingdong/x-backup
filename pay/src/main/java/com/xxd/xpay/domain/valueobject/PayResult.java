package com.xxd.xpay.domain.valueobject;

import com.xxd.xpay.domain.enums.PayOrderStatusEnum;
import lombok.Data;

@Data
public class PayResult {
    private String prepayId;
    private String channelNo;
    private PayOrderStatusEnum payOrderStatusEnum;
}
