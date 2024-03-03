package com.xxd.xpay.strategy.wechat;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
public class RefundResponse {
    @NotNull
    private String refundId;
    @NotNull
    private String outRefundNo;
    @NotNull
    private String transactionId;
    @NotNull
    private String outTradeNo;
    @NotNull
    private String channel;
    @NotNull
    private String userReceivedAccount;
    private String successTime;
    @NotNull
    private String createTime;
    @NotNull
    private String status;
    private String fundsAccount;
    @NotNull
    private Amount amount;
    private List<PromotionDetail> promotionDetail;

    @Data
    private static class Amount {
        @NotNull
        private Integer total;
        @NotNull
        private Integer refund;
        private List<From> from;
        @NotNull
        private Integer payerTotal;
        @NotNull
        private Integer payerRefund;
        @NotNull
        private Integer settlementRefund;
        @NotNull
        private Integer settlementTotal;
        @NotNull
        private Integer discountRefund;
        @NotNull
        private String currency;
        private Integer refundFee;
    }

    @Data
    private static class From {
        @NotNull
        private String account;
        @NotNull
        private Integer amount;
    }

    @Data
    public static class PromotionDetail {
        @NotNull
        private String promotionId;
        @NotNull
        private String scope;
        @NotNull
        private String type;
        @NotNull
        private Integer amount;
        @NotNull
        private Integer refundAmount;
        private List<GoodsDetail> goodsDetail;

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
}
