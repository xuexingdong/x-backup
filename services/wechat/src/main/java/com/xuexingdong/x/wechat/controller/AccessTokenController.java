package com.xxd.x.wechat.controller;

import com.xxd.x.common.http.XMonoResp;
import com.xxd.x.wechat.config.WechatConfig;
import com.xxd.x.wechat.service.WechatService;
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
