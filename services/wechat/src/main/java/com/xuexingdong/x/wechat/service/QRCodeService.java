package com.xxd.x.wechat.service;


import com.xxd.x.wechat.enumeration.QRCodeScanStatus;
import com.xxd.x.wechat.model.QRCode;

import java.util.Optional;

public interface QRCodeService {

    QRCode getQRCode();

    QRCodeScanStatus getScanStatus(String token);

    Optional<String> getQRCodeRedirectUri(String token);
}
