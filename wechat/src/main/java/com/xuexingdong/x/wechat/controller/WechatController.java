package com.xuexingdong.x.wechat.controller;

import com.xuexingdong.x.common.crypto.CryptoException;
import com.xuexingdong.x.common.crypto.XCrypto;
import com.xuexingdong.x.common.serialization.XSerialization;
import com.xuexingdong.x.wechat.config.WechatConfig;
import com.xuexingdong.x.wechat.plugin.WechatPlugin;
import com.xuexingdong.x.wechat.sdk.message.response.WechatResponseMessage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("wechat")
public class WechatController {

    private static final Logger logger = LoggerFactory.getLogger(WechatController.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private WechatConfig wechatConfig;

    private List<WechatPlugin> plugins;

    @GetMapping
    public String validToken(@RequestParam String signature, @RequestParam String timestamp, @RequestParam String nonce, @RequestParam String echostr) {
        String[] array = new String[]{wechatConfig.getToken(), timestamp, nonce};
        Arrays.sort(array);
        String sha1Str;
        try {
            sha1Str = XCrypto.SHA1.encrypt(String.join("", array));
        } catch (CryptoException e) {
            logger.error("Error occurred when doing SHA1 encrypting");
            return "";
        }
        if (!Objects.equals(sha1Str, signature)) {
            logger.info("The encrypted string doesn't equals signature");
            return "";
        }
        return echostr;
    }

    @PostMapping
    public String handleMessage(@RequestParam(name = "msg_signature", required = false) String msgSignature, @RequestParam String timestamp, @RequestParam String nonce, @RequestBody String xml) {
        String result = "success";
        SAXReader sax = new SAXReader();
        Document document;
        try {
            document = sax.read(new StringReader(xml));
        } catch (DocumentException e) {
            logger.error("Parsing origin wechat XML error: {}", e.getMessage());
            return result;
        }
        for (WechatPlugin plugin : plugins) {
            Optional<? extends WechatResponseMessage> resp = plugin.handle(document);
            if (resp.isPresent()) {
                result = XSerialization.toXML(resp.get(), "xml");
                break;
            }
        }
        return result;
    }

    @PostConstruct
    public void init() {
        Map<String, WechatPlugin> pluginMap = applicationContext.getBeansOfType(WechatPlugin.class);
        this.plugins = pluginMap.entrySet().stream().sorted(Comparator.comparingInt(o -> o.getValue().order()))
                .map(Map.Entry::getValue).collect(Collectors.toList());
    }
}
