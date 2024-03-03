package com.xxd.xpay.domain.domainservice.impl;

import com.xxd.xpay.domain.aggregate.App;
import com.xxd.xpay.domain.aggregate.PayOrder;
import com.xxd.xpay.domain.domainservice.PayDomainService;
import com.xxd.xpay.domain.exception.XPayException;
import com.xxd.xpay.domain.factory.PayOrderFactory;
import com.xxd.xpay.domain.repository.AppRepository;
import com.xxd.xpay.domain.repository.PayOrderRepository;
import com.xxd.xpay.domain.valueobject.PayContext;
import com.xxd.xpay.domain.valueobject.RefundContext;
import com.xxd.xpay.strategy.PayStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class PayDomainServiceImpl implements PayDomainService {

    private final AppRepository appRepository;

    private final PayOrderFactory payOrderFactory;

    private final PayOrderRepository payOrderRepository;

    private final Map<String, PayStrategy> payStrategyMap;

    public PayDomainServiceImpl(AppRepository appRepository, PayOrderRepository payOrderRepository, PayOrderFactory payOrderFactory, Map<String, PayStrategy> payStrategyMap) {
        this.appRepository = appRepository;
        this.payOrderRepository = payOrderRepository;
        this.payOrderFactory = payOrderFactory;
        this.payStrategyMap = payStrategyMap;
    }

    @Override
    public PayOrder create(PayContext payContext) {
        validate(payContext);
        App app = appRepository.findByAppId(payContext.getAppId()).orElseThrow(() -> new XPayException("APPID不存在"));
        payOrderRepository.findByAppIdAndOutTradeNo(app.getAppId(), payContext.getOutTradeNo()).orElseThrow(() -> new XPayException("商户订单号重复"));
        PayOrder payOrder = payOrderFactory.create(payContext);
        payOrderRepository.save(payOrder);
        return payOrder;
    }

    private void validate(PayContext payContext) {
        validateOutTradeNo(payContext.getOutTradeNo());
    }

    @Override
    public void validate(RefundContext refundContext) {

    }

    private void validateOutTradeNo(String outTradeNo) {
        if (Objects.isNull(outTradeNo)) {
            throw new XPayException("商户订单号不存在");
        }
        // TODO 优化校验器
        if (outTradeNo.length() < 6
                || outTradeNo.length() > 32) {
            throw new XPayException("商户订单号长度不符合规范");
        }
    }

    @Override
    public PayOrder pay(PayOrder payOrder) {
        PayStrategy payStrategy = getStrategy(payOrder);
        String thirdPayRequest = payStrategy.pay(payOrder);
        return null;
    }

    private PayStrategy getStrategy(PayOrder payOrder) {
        return payStrategyMap.get(payOrder.getPayTypeCode());
    }

    private String buildThirdPayRequest(PayOrder payOrder) {
        payStrategyMap.get(payOrder.getPayTypeCpde())
    }
}
