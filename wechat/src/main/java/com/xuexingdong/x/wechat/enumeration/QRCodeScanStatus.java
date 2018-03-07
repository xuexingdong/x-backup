package com.xuexingdong.x.wechat.enumeration;

public enum QRCodeScanStatus {
    WAITING("等待扫码"), SUCCESS("扫描成功"), EXPIRED("二维码过期"), CONFIRM("确认登录");

    private final String text;

    QRCodeScanStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
