package com.xxd.x.wechat.service.impl;

import com.xxd.x.common.http.XHttp;
import com.xxd.x.common.serialization.XSerialization;
import com.xxd.x.common.utils.XRegexUtils;
import com.xxd.x.wechat.enumeration.QRCodeScanStatus;
import com.xxd.x.wechat.model.QRCode;
import com.xxd.x.wechat.service.QRCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Qualifier("tmall")
public class TmallQRCodeServiceImpl implements QRCodeService {

    private static final Logger logger = LoggerFactory.getLogger(TmallQRCodeServiceImpl.class);

    private static final Map<String, QRCodeScanStatus> QRCODE_SCAN_STATUS_MAP = new HashMap<String, QRCodeScanStatus>() {{
        put("10000", QRCodeScanStatus.WAITING);
        put("10001", QRCodeScanStatus.SUCCESS);
        put("10004", QRCodeScanStatus.EXPIRED);
        put("10006", QRCodeScanStatus.CONFIRM);
    }};

    public static final String TMALL_COOKIES_POOL_REDIS_KEY = "cookies_pool:tmall";

    public static final String QRCODE_GENERATE_URL = "https://qrlogin.taobao.com/qrcodelogin/generateQRCode4Login.do";

    public static final String QRCODE_STATUS_CHECK_URL = "https://qrlogin.taobao.com/qrcodelogin/qrcodeLoginCheck.do";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public QRCode getQRCode() {
        QRCode qrCode = new QRCode();
        String body;
        try {
            body = XHttp.body(QRCODE_GENERATE_URL);
        } catch (IOException e) {
            return qrCode;
        }
        Optional<String> qrCodeUrl = XRegexUtils.findOne(body, "(?<=\"url\":\")\\S+\\.png");
        Optional<String> lgToken = XRegexUtils.findOne(body, "(?<=\"lgToken\":\")\\S+?(?=\")");
        if (!qrCodeUrl.isPresent() || !lgToken.isPresent()) {
            return qrCode;
        }
        logger.info("Tmall login qrcode url: {}, lgToken: ", qrCodeUrl.get(), lgToken.get());
        qrCode.setUrl(qrCodeUrl.get());
        qrCode.setToken(lgToken.get());
        return qrCode;
    }

    @Override
    public QRCodeScanStatus getScanStatus(String token) {
        String body;
        try {
            body = XHttp.body(QRCODE_STATUS_CHECK_URL + "?lgToken=" + token);
        } catch (IOException e) {
            return null;
        }
        Map<String, String> map = XSerialization.toMap(body, String.class, String.class);
        QRCodeScanStatus scanStatus = QRCODE_SCAN_STATUS_MAP.get(map.get("code"));
        logger.info("Tmall qrcode scan status is: {}", scanStatus);
        if (scanStatus == QRCodeScanStatus.EXPIRED) {
            stringRedisTemplate.delete(token);
        }
        if (scanStatus == QRCodeScanStatus.CONFIRM) {
            String redirectUri = map.get("url");
            Optional<String> username = XRegexUtils.findOne(redirectUri, "(?<=uid=)\\S+(?=&token)");
            username.ifPresent(name -> {
                try {
                    stringRedisTemplate.opsForValue().set(token, URLDecoder.decode(username.get(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    logger.error("URL decode error");
                }
            });
            Map<String, String> cookies;
            try {
                cookies = XHttp.cookies(redirectUri);
            } catch (IOException e) {
                logger.error("Get tmall login cookies error");
                return scanStatus;
            }
            logger.info("Cookies are: {}", cookies);
            stringRedisTemplate.opsForList().rightPush(TMALL_COOKIES_POOL_REDIS_KEY, XSerialization.toJSON(cookies));
        }
        return scanStatus;
    }

    @Override
    public Optional<String> getQRCodeRedirectUri(String token) {
        Boolean exists = stringRedisTemplate.hasKey(token);
        if (Objects.nonNull(exists) && exists) {
            return Optional.ofNullable(stringRedisTemplate.opsForValue().get(token));
        }
        return Optional.empty();
    }

}
