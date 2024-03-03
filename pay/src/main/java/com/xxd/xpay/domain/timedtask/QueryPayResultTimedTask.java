package com.xxd.xpay.domain.timedtask;

import com.xxd.xpay.application.PayService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryPayResultTimedTask {

    @Autowired
    private PayService payService;

    @XxlJob("queryPayOrderResult")
    public ReturnT<String> queryPayOrderResult() {
        payService.batchQueryResult();
        return ReturnT.SUCCESS;
    }
}
