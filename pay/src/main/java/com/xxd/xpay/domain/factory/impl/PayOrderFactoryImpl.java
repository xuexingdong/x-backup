package com.xxd.xpay.domain.factory.impl;

import com.xxd.xpay.domain.aggregate.PayOrder;
import com.xxd.xpay.domain.enums.OrderTypeEnum;
import com.xxd.xpay.domain.factory.PayOrderFactory;
import com.xxd.xpay.domain.valueobject.PayContext;
import com.xxd.xpay.domain.valueobject.RefundContext;
import org.springframework.stereotype.Component;

@Component
public class PayOrderFactoryImpl implements PayOrderFactory {

    @Override
    public PayOrder create(PayContext payContext) {
        PayOrder payOrder = new PayOrder();
        payOrder.setOrderType(OrderTypeEnum.PAY);
        return payOrder;
    }

    @Override
    public PayOrder create(PayOrder payOrder, RefundContext refundContext) {
        return null;
    }
}
