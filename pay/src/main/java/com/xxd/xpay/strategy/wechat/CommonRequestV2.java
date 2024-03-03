package com.xxd.xpay.strategy.wechat;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
public class CommonRequestV2 {
    @NotNull
    private String appId;
    @NotNull
    private String mchId;
    private String deviceInfo;
    @NotNull
    private String nonceStr;
    @NotNull
    private String sign;
    private String signType;
    @NotNull
    private String body;
    //    2018-06-08T10:34:56+08:00
    private String timeExpire;
    private String attach;
    //    https://www.weixin.qq.com/wxpay/pay.php
    @NotNull
    private String notifyUrl;
    private String goodsTag;
    private String supportFapiao;
    @NotNull
    private CommonRequestV2.Amount amount;
    @NotNull
    private CommonRequestV2.Payer payer;
    private CommonRequestV2.Detail detail;
    private CommonRequestV2.SceneInfo sceneInfo;
    private CommonRequestV2.SettleInfo settleInfo;

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

        private List<CommonRequestV2.Detail.GoodsDetail> goodsDetail;

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

        private CommonRequestV2.SceneInfo.StoreInfo storeInfo;

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
