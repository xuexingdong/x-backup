package com.xuexingdong.x.wechat.controller;

import com.xuexingdong.x.common.http.XMonoResp;
import com.xuexingdong.x.common.utils.XDateTimeUtils;
import com.xuexingdong.x.common.utils.XRandomUtils;
import com.xuexingdong.x.wechat.config.WechatConfig;
import com.xuexingdong.x.wechat.model.JsSdkConfig;
import com.xuexingdong.x.wechat.service.WechatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("wechat")
public class JsSdkConfigController {

    private static final Logger logger = LoggerFactory.getLogger(JsSdkConfigController.class);

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private WechatService wechatService;

    @GetMapping("jsapi_ticket")
    public XMonoResp<JsSdkConfig> getConfig() {
        JsSdkConfig jsSdkConfig = new JsSdkConfig();
        jsSdkConfig.setAppId(wechatConfig.getAppid());
        jsSdkConfig.setTimestamp(XDateTimeUtils.get13TimestampStr());
        jsSdkConfig.setNonceStr(XRandomUtils.nextString(6));
        String[] array = new String[]{wechatConfig.getToken(), jsSdkConfig.getTimestamp(), jsSdkConfig.getNonceStr()};
        Arrays.sort(array);
        return XMonoResp.data(jsSdkConfig);
    }
}
