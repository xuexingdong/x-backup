package com.xxd.xpay.domain.aggregate;

import lombok.Data;

@Data
public class PayType {
    private Long id;
    private String payTypeCode;
    private String payTypeName;

}
