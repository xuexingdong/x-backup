package com.xxd.xpay.domain.repository;

import com.xxd.xpay.domain.aggregate.PayOrder;

import java.util.Optional;

public interface PayOrderRepository {

    Optional<PayOrder> findByAppIdAndOutTradeNo(String appId, String outTradeNo);

    Optional<PayOrder> findByMchIdAndOutTradeNo(String mchId, String outTradeNo);

    Optional<PayOrder> findByMchIdAndOutRefundNo(String mchId, String outRefundNo);

    Optional<PayOrder> findByMchIdAndPaySerialNo(String mchId, String paySerialNo);

    void save(PayOrder payOrder);
}
