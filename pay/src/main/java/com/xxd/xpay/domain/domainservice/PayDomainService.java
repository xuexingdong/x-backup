package com.xxd.xpay.domain.domainservice;

import com.xxd.xpay.domain.aggregate.PayOrder;
import com.xxd.xpay.domain.valueobject.PayContext;
import com.xxd.xpay.domain.valueobject.RefundContext;

public interface PayDomainService {

    void validate(PayContext payContext);

    void validate(RefundContext refundContext);

    PayOrder create(PayContext payContext);

    PayOrder pay(PayOrder payOrder);

}
