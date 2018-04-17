package com.xuexingdong.crawler.service;
import com.xuexingdong.crawler.enumeration.QRCodeScanStatus;
import com.xuexingdong.crawler.model.QRCode;

import java.util.Optional;

public interface QRCodeService {

    QRCode getQRCode();

    QRCodeScanStatus getScanStatus(String token);

    Optional<String> getQRCodeRedirectUri(String token);
}
