package com.xxd.xpay.strategy.wechat;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
public class PayResponse {
    @NotNull
    private String appid;
    @NotNull
    private String mchid;
    @NotNull
    private String outTradeNo;
    private String transactionId;
    private String tradeType;
    @NotNull
    private String tradeState;
    @NotNull
    private String tradeStateDesc;
    private String bankType;
    private String attach;
    private String successTime;
    @NotNull
    private Payer payer;
    @NotNull
    private Amount amount;
    private SceneInfo sceneInfo;
    private List<PromotionDetail> promotionDetail;

    @Data
    private static class Payer {
        @NotNull
        private String openid;
    }

    @Data
    private static class Amount {
        private Integer total;

        private Integer payerTotal;

        private String currency;

        private String payerCurrency;
    }

    @Data
    private static class SceneInfo {
        private String deviceId;
    }

    @Data
    public static class PromotionDetail {
        @NotNull
        private String couponId;
        private String name;
        private String scope;
        private String type;
        @NotNull
        private Integer amount;
        private String stockId;
        private Integer wechatpayContribute;
        private Integer merchantContribute;
        private Integer otherContribute;
        private String currency;
        private List<GoodsDetail> goodsDetail;

        @Data
        private static class GoodsDetail {
            @NotNull
            private String goodsId;
            @NotNull
            private Integer quantity;
            @NotNull
            private Integer unitPrice;
            @NotNull
            private Integer discountAmount;
            private String goodsRemark;
        }
    }
}
