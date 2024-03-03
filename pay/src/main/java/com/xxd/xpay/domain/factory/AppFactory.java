package com.xxd.xpay.domain.factory;

import com.xxd.xpay.domain.aggregate.App;
import com.xxd.xpay.domain.valueobject.PayContext;

public interface AppFactory {
    App create(PayContext payContext);
}
