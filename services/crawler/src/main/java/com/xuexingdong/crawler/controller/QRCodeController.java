package com.xuexingdong.crawler.controller;

import com.xuexingdong.crawler.enumeration.QRCodeScanStatus;
import com.xuexingdong.crawler.enumeration.QRCodeType;
import com.xuexingdong.crawler.model.QRCode;
import com.xuexingdong.crawler.service.QRCodeService;
import com.xuexingdong.x.common.http.XMonoResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/qrcode")
public class QRCodeController {

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
        if (!username.isPresent()) {
            return null;
        }
        return XMonoResp.data(username.get());
    }
}
