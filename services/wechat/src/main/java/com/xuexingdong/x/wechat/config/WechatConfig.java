package com.xxd.x.wechat.config;

import com.xxd.x.wechat.sdk.WechatClient;
import com.xxd.x.wechat.sdk.constant.WechatEncryptMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotEmpty;

@Configuration
@ConfigurationProperties("wechat")
public class WechatConfig {

    @NotEmpty
    private String appid;

    @NotEmpty
    private String appsecret;

    private String token;

    private String aeskey;

    @Value("${:CLEAR}")
    private WechatEncryptMode encryptMode;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAeskey() {
        return aeskey;
    }

    public void setAeskey(String aeskey) {
        this.aeskey = aeskey;
    }

    public WechatEncryptMode getEncryptMode() {
        return encryptMode;
    }

    public void setEncryptMode(WechatEncryptMode encryptMode) {
        this.encryptMode = encryptMode;
    }

    @Bean
    public WechatClient wechatClient() {
        return new WechatClient(appid, appsecret);
    }
}
