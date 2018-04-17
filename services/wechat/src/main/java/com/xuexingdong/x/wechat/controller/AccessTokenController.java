package com.xuexingdong.x.wechat.controller;

import com.xuexingdong.x.common.http.XMonoResp;
import com.xuexingdong.x.wechat.config.WechatConfig;
import com.xuexingdong.x.wechat.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("wechat")
public class AccessTokenController {

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private WechatService wechatService;

    @GetMapping(value = "access_token")
    public XMonoResp<String> getAccessToken() {
        return XMonoResp.data(wechatService.getAccessToken());
    }
}
