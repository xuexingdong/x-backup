package com.xxd.xpay.strategy.wechat;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RefundRequest {
    /**
     * transactionId, outTradeNo 二选一
     */
    private String transactionId;
    private String outTradeNo;
    @NotNull
    private String outRefundNo;
    private String reason;
    private String notifyUrl;
    private String fundsAccount;
    @NotNull
    private Amount amount;
    private List<GoodsDetail> goodsDetail;

    @Data
    public static class Amount {
        @NotNull
        private Integer refund;
        private List<From> from;
        @NotNull
        private Integer total;
        @NotNull
        private String currency;
    }

    @Data
    private static class From {
        @NotNull
        private String account;
        @NotNull
        private Integer amount;
    }

    @Data
    private static class GoodsDetail {
        @NotNull
        private String merchantGoodsId;
        private String wechatpayGoodsId;
        private String goodsName;
        @NotNull
        private Integer unitPrice;
        @NotNull
        private Integer refundAmount;
        @NotNull
        private Integer refundQuantity;
    }
}
