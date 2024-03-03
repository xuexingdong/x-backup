package com.xxd.xpay.application;

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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class PayServiceImpl implements PayService {

    private final AppRepository appRepository;

    private final PayOrderFactory payOrderFactory;

    private final PayOrderRepository payOrderRepository;

    private final Map<String, PayStrategy> payStrategyMap;

    @Autowired
    private PayDomainService payDomainService;

    public PayServiceImpl(AppRepository appRepository, PayOrderFactory payOrderFactory, PayOrderRepository payOrderRepository, Map<String, PayStrategy> payStrategyMap) {
        this.appRepository = appRepository;
        this.payOrderFactory = payOrderFactory;
        this.payOrderRepository = payOrderRepository;
        this.payStrategyMap = payStrategyMap;
    }

    @Override
    public PayOrder jsapiPay(PayContext payContext) {
        payDomainService.validate(payContext);
        App app = appRepository.findByAppId(payContext.getAppId()).orElseThrow(() -> new XPayException("APPID不存在"));
        payOrderRepository.findByAppIdAndOutTradeNo(app.getAppId(), payContext.getOutTradeNo()).orElseThrow(() -> new XPayException("商户订单号重复"));
        PayOrder payOrder = payOrderFactory.create(payContext);
        payOrderRepository.save(payOrder);
        payDomainService.pay(payOrder);
        return payOrder;
    }

    @Override
    public PayOrder barCodePay(PayContext payContext) {
        payDomainService.validate(payContext);
        App app = appRepository.findByAppId(payContext.getAppId()).orElseThrow(() -> new XPayException("APPID不存在"));
        payOrderRepository.findByAppIdAndOutTradeNo(app.getAppId(), payContext.getOutTradeNo()).orElseThrow(() -> new XPayException("商户订单号重复"));
        PayOrder payOrder = payOrderFactory.create(payContext);
        payOrderRepository.save(payOrder);
        return payOrder;
    }

    @Override
    public PayOrder refund(RefundContext refundContext) {
        payDomainService.validate(refundContext);
        App app = appRepository.findByAppId(refundContext.getAppId()).orElseThrow(() -> new XPayException("APPID不存在"));
        if (StringUtils.isNotEmpty(refundContext.getOutRefundNo())) {
            Optional<PayOrder> payOrderOptional = payOrderRepository.findByMchIdAndOutRefundNo(refundContext.getMchId(), refundContext.getOutRefundNo());
            if (payOrderOptional.isPresent()) {
//                重复退款单
            }
        }
        PayOrder payOrder = payOrderRepository.findByMchIdAndOutTradeNo(refundContext.getMchId(), refundContext.getOutTradeNo()).orElseThrow(() -> new XPayException("原订单不存在"));
        PayOrder refundOrder = payOrderFactory.create(payOrder, refundContext);
        payOrderRepository.save(refundOrder);
        return refundOrder;
    }

    @Override
    public void batchQueryResult() {

    }

    @Override
    public void notifyPayResult() {

    }
}
