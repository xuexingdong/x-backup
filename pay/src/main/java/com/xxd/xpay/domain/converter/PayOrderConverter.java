package com.xxd.xpay.domain.converter;

import com.xxd.xpay.domain.aggregate.PayOrder;
import com.xxd.xpay.infrastructure.dao.PayOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayOrderConverter {
    PayOrderConverter INSTANCE = Mappers.getMapper(PayOrderConverter.class);

    PayOrder do2domain(PayOrderDO payOrderDO);
}
