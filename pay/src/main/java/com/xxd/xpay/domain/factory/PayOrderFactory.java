package com.xxd.xpay.domain.factory;

import com.xxd.xpay.domain.aggregate.PayOrder;
import com.xxd.xpay.domain.valueobject.PayContext;
import com.xxd.xpay.domain.valueobject.RefundContext;

public interface PayOrderFactory {
    PayOrder create(PayContext payContext);

    PayOrder create(PayOrder payOrder, RefundContext refundContext);
}
