package com.xuexingdong.x.wechat.interceptor;

import com.xuexingdong.x.wechat.config.WechatConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// judge if the request is from wechat ip
public class WechatServerInterceptor implements HandlerInterceptor {

    @Autowired
    private WechatConfig wechatConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }


}
