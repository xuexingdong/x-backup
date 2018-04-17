package com.xuexingdong.x.wechat.service;


import com.xuexingdong.x.wechat.enumeration.QRCodeScanStatus;
import com.xuexingdong.x.wechat.model.QRCode;

import java.util.Optional;

public interface QRCodeService {

    QRCode getQRCode();

    QRCodeScanStatus getScanStatus(String token);

    Optional<String> getQRCodeRedirectUri(String token);
}
