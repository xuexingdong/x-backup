package com.xxd.xpay.strategy.wechat;

import com.xxd.xpay.domain.aggregate.PayOrder;
import com.xxd.xpay.domain.exception.XPayException;
import com.xxd.xpay.domain.repository.PayOrderRepository;
import com.xxd.xpay.domain.valueobject.PayResult;
import com.xxd.xpay.domain.valueobject.RefundResult;
import com.xxd.xpay.strategy.PayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.io.IOException;
import java.math.BigDecimal;

@Component
public class WechatPayStrategy implements PayStrategy {

    @Autowired
    private WechatPayService wechatPayService;

    @Autowired
    private PayOrderRepository payOrderRepository;

    @Override
    public PayResult pay(PayOrder payOrder) {
        PayRequest payRequest = buildPayRequest(payOrder);
        Response<JsapiResponse> execute;
        try {
            execute = wechatPayService.jsapiPay(payRequest).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsapiResponse jsapiResponse = execute.body();
        PayResult payResult = buildPayResult(jsapiResponse);
        return payResult;
    }

    @Override
    public RefundResult refund(PayOrder refundOrder) {
        PayOrder payOrder = payOrderRepository.findByMchIdAndPaySerialNo(refundOrder.getMchId(), refundOrder.getPaySerialNo()).orElseThrow(() -> new XPayException("原订单不存在"));
        RefundRequest refundRequest = buildRefundRequest(refundOrder, payOrder);
        Response<RefundResponse> execute;
        try {
            execute = wechatPayService.refund(refundRequest).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        RefundResponse refundResponse = execute.body();
        refundResponse.getRefundId()
        RefundResult refundResult = buildRefundResult(refundResponse);
        refundResult.setChannelNo();
        return refundResult;
    }

    private RefundResult buildRefundResult(RefundResponse refundResponse) {
        return null;
    }

    private RefundRequest buildRefundRequest(PayOrder refundOrder, PayOrder payOrder) {
        RefundRequest.Amount amount = new RefundRequest.Amount();
        amount.setRefund(refundOrder.getAmount().abs().multiply(new BigDecimal(100)).intValue());
        amount.setTotal(payOrder.getAmount().multiply(new BigDecimal(100)).intValue());
        amount.setCurrency("CNY");
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOutRefundNo(refundOrder.getPaySerialNo());
        refundRequest.setReason(refundRequest.getReason());
        refundRequest.setNotifyUrl(refundOrder.getNotifyUrl());
        refundRequest.setOutTradeNo(refundOrder.getOutTradeNo());
        refundRequest.setAmount(amount);
        return refundRequest;
    }

    private PayRequest buildPayRequest(PayOrder payOrder) {
        PayRequest payRequest = new PayRequest();
        return payRequest;
    }

    private PayResult buildPayResult(JsapiResponse jsapiResponse) {
        PayResult payResult = new PayResult();
        return payResult;
    }
}
