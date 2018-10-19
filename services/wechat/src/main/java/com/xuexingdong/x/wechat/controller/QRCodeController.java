package com.xuexingdong.x.wechat.controller;

import com.xuexingdong.x.common.http.XMonoResp;
import com.xuexingdong.x.wechat.enumeration.QRCodeScanStatus;
import com.xuexingdong.x.wechat.enumeration.QRCodeType;
import com.xuexingdong.x.wechat.model.QRCode;
import com.xuexingdong.x.wechat.service.QRCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("qrcode")
public class QRCodeController {

    public static final String QRCODE_GENERATE_URL = "https://qrlogin.taobao.com/qrcodelogin/generateQRCode4Login.do";

    public static final String QRCODE_STATUS_CHECK_URL = "https://qrlogin.taobao.com/qrcodelogin/qrcodeLoginCheck.do";

    private static final Logger logger = LoggerFactory.getLogger(QRCodeController.class);

    @Autowired
    @Qualifier("tmall")
    private QRCodeService tmallQRCodeService;

    @GetMapping
    public XMonoResp<QRCode> getQRCodeUrl(@RequestParam QRCodeType type) {
        QRCode qrCode = null;
        switch (type) {
            case WECHAT:
                break;
            case TMALL:
                qrCode = tmallQRCodeService.getQRCode();
                break;
            default:
                return null;

        }
        return XMonoResp.data(qrCode);
    }

    @GetMapping("/scan_status")
    public XMonoResp<QRCodeScanStatus> getQRCodeScanStatus(@RequestParam QRCodeType type, @RequestParam String token) {
        QRCodeScanStatus scanStatus = null;
        switch (type) {
            case WECHAT:
                break;
            case TMALL:
                scanStatus = tmallQRCodeService.getScanStatus(token);
                break;
            default:
                break;
        }
        return XMonoResp.data(scanStatus);
    }

    @GetMapping("/redirect_uri")
    public XMonoResp<String> getQRCodeRedirectUri(@RequestParam QRCodeType type, @RequestParam String token) {
        Optional<String> username = Optional.empty();
        switch (type) {
            case WECHAT:
                break;
            case TMALL:
                username = tmallQRCodeService.getQRCodeRedirectUri(token);
                break;
            default:
                break;
        }
        return username.map(XMonoResp::data).orElse(null);
    }
}
