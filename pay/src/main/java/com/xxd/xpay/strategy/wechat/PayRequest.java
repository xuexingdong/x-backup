package com.xxd.xpay.strategy.wechat;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PayRequest {
    @NotNull
    private String appid;
    @NotNull
    private String mchid;
    @NotNull
    private String description;
    @NotNull
    private String outTradeNo;
    //    2018-06-08T10:34:56+08:00
    private String timeExpire;
    private String attach;
    //    https://www.weixin.qq.com/wxpay/pay.php
    @NotNull
    private String notifyUrl;
    private String goodsTag;
    private String supportFapiao;
    @NotNull
    private Amount amount;
    @NotNull
    private Payer payer;
    private Detail detail;
    private SceneInfo sceneInfo;
    private SettleInfo settleInfo;

    @Data
    private static class Amount {
        @NotNull
        private Integer total;

        private String currency;
    }

    @Data
    private static class Payer {
        @NotNull
        private String openid;
    }

    @Data
    private static class Detail {
        private Integer costPrice;
        private String invoiceId;

        private List<GoodsDetail> goodsDetail;

        @Data
        private static class GoodsDetail {
            @NotNull
            private String merchantGoodsId;
            private String wechatpayGoodsId;
            private String goodsName;
            @NotNull
            private Integer quantity;
            @NotNull
            private Integer unitPrice;
        }
    }

    @Data
    private static class SceneInfo {
        @NotNull
        private String payerClientIp;

        private String deviceId;

        private StoreInfo storeInfo;

        @Data
        private static class StoreInfo {
            @NotNull
            private String id;
            private String name;
            private String areaCode;
            private String address;
        }
    }

    @Data
    private static class SettleInfo {
        private Boolean profitSharing;
    }
}
