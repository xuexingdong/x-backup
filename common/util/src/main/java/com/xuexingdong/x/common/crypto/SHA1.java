package com.xuexingdong.x.common.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import static com.xuexingdong.x.common.crypto.Constants.HEX_DIGITS;

public class SHA1 implements Encryption {

    @Override
    public String encrypt(String src) throws CryptoException {
        if (src == null || src.isEmpty()) {
            return null;
        }
        MessageDigest md;
        Security.addProvider(new BouncyCastleProvider());
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(e.getMessage());
        }
        md.update(src.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // 把密文转换成十六进制的字符串形式
        for (byte b : bytes) {
            sb.append(HEX_DIGITS[(b >> 4) & 0x0f]);
            sb.append(HEX_DIGITS[b & 0x0f]);
        }
        return sb.toString();
    }
}
