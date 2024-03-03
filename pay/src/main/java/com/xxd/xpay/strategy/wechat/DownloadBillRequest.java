package com.xxd.xpay.strategy.wechat;

import lombok.Data;

@Data
public class DownloadBillRequest {
    private String hashType;
    private String hashValue;
    private String downloadUrl;
}
