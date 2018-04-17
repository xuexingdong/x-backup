package com.xuexingdong.x.wechat.aop;

import com.xuexingdong.x.common.utils.XDateTimeUtils;
import com.xuexingdong.x.common.utils.XRandomUtils;
import com.xuexingdong.x.wechat.config.WechatConfig;
import com.xuexingdong.x.wechat.sdk.aes.AesException;
import com.xuexingdong.x.wechat.sdk.aes.WXBizMsgCrypt;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.util.Objects;

@Aspect
@Component
@ConditionalOnProperty(name = "wechat.encrypt-mode", havingValue = "CIPHER")
public class WechatAESAspect {

    private static final Logger logger = LoggerFactory.getLogger(WechatAESAspect.class);

    @Autowired
    private WechatConfig wechatConfig;

    @Around("execution(* com.xuexingdong.x.wechat.controller..WechatController.handleMessage(..))")
    public String messagePointcut(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        String msgSignature = (String) args[0];
        String timestamp = (String) args[1];
        String nonce = (String) args[2];
        String xml = (String) args[3];
        String result = "success";
        SAXReader sax = new SAXReader();
        Document document;
        try {
            document = sax.read(new StringReader(xml));
        } catch (DocumentException e) {
            logger.error("Parse origin wechat XML error");
            return result;
        }
        Element root = document.getRootElement();
        if (Objects.isNull(root.element("Encrypt"))) {
            logger.error("No <Encrypt> element found in XML");
            return result;
        }
        WXBizMsgCrypt pc;
        try {
            pc = new WXBizMsgCrypt(wechatConfig.getToken(), wechatConfig.getAeskey(), wechatConfig.getAppid());
        } catch (AesException e) {
            logger.error(e.getMessage());
            return result;
        }
        try {
            xml = pc.decryptMsg(msgSignature, timestamp, nonce, xml);
        } catch (AesException e) {
            logger.error(e.getMessage());
            return result;
        }
        args[3] = xml;
        result = (String) point.proceed(args);

        timestamp = XDateTimeUtils.get10TimestampStr();
        nonce = XRandomUtils.nextString(6);
        try {
            result = pc.encryptMsg(result, timestamp, nonce);
        } catch (AesException e) {
            logger.error(e.getMessage());
            return result;
        }
        return result;
    }
}
