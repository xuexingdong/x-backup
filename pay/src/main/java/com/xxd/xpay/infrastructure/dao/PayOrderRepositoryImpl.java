package com.xxd.xpay.infrastructure.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xxd.xpay.domain.aggregate.PayOrder;
import com.xxd.xpay.domain.converter.PayOrderConverter;
import com.xxd.xpay.domain.repository.PayOrderRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PayOrderRepositoryImpl implements PayOrderRepository {

    private final PayOrderMapper payOrderMapper;

    public PayOrderRepositoryImpl(PayOrderMapper payOrderMapper) {
        this.payOrderMapper = payOrderMapper;
    }

    @Override
    public Optional<PayOrder> findByAppIdAndOutTradeNo(String appId, String outTradeNo) {
        LambdaQueryWrapper<PayOrderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(PayOrderDO::getOutRefundNo, outRefundNo);
        return Optional.empty();
    }

    @Override
    public Optional<PayOrder> findByMchIdAndOutTradeNo(String mchId, String outTradeNo) {
        LambdaQueryWrapper<PayOrderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(PayOrderDO::getMchId, mchId);
        queryWrapper.eq(PayOrderDO::getOutTradeNo, outTradeNo);
        PayOrderDO payOrderDO = payOrderMapper.selectOne(queryWrapper);
        return Optional.ofNullable(PayOrderConverter.INSTANCE.do2domain(payOrderDO));
    }

    @Override
    public Optional<PayOrder> findByMchIdAndOutRefundNo(String mchId, String outRefundNo) {
        LambdaQueryWrapper<PayOrderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(PayOrderDO::getMchId, mchId);
        queryWrapper.eq(PayOrderDO::getOutRefundNo, outRefundNo);
        PayOrderDO payOrderDO = payOrderMapper.selectOne(queryWrapper);
        return Optional.ofNullable(PayOrderConverter.INSTANCE.do2domain(payOrderDO));
    }

    @Override
    public Optional<PayOrder> findByMchIdAndPaySerialNo(String mchId, String paySerialNo) {
        LambdaQueryWrapper<PayOrderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(PayOrderDO::getMchId, mchId);
        queryWrapper.eq(PayOrderDO::getPaySerialNo, paySerialNo);
        PayOrderDO payOrderDO = payOrderMapper.selectOne(queryWrapper);
        return Optional.ofNullable(PayOrderConverter.INSTANCE.do2domain(payOrderDO));
    }

    @Override
    public void save(PayOrder payOrder) {

    }
}
