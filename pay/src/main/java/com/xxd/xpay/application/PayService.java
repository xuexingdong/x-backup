package com.xxd.xpay.application;

import com.xxd.xpay.domain.aggregate.PayOrder;
import com.xxd.xpay.domain.valueobject.PayContext;
import com.xxd.xpay.domain.valueobject.RefundContext;

public interface PayService {

    PayOrder jsapiPay(PayContext payContext);

    PayOrder barCodePay(PayContext payContext);

    PayOrder refund(RefundContext refundContext);

    void batchQueryResult();

    void notifyPayResult();
}
