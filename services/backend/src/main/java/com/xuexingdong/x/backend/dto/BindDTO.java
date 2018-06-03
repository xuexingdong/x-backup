package com.xuexingdong.x.backend.dto;

import javax.validation.constraints.NotEmpty;

public class BindDTO {
    @NotEmpty
    private String openid;
    @NotEmpty
    private String secretKey;
    @NotEmpty
    private String remarkName;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }
}
