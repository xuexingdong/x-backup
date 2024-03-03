package com.xxd.xpay.strategy;

import com.xxd.xpay.domain.aggregate.PayOrder;
import com.xxd.xpay.domain.valueobject.PayResult;

public interface PayStrategy {

    PayResult pay(PayOrder payOrder);

    RefundResult refund(PayOrder refundOrder);

}
