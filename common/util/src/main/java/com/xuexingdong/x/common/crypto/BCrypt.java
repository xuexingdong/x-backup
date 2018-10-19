package com.xuexingdong.x.common.crypto;

import org.bouncycastle.crypto.generators.OpenBSDBCrypt;

public class BCrypt implements EncryptionWithSalt {

    private static final int COST = 4;

    @Override
    public String encrypt(String src) throws CryptoException {
        return encrypt(src, "");
    }

    @Override
    public String encrypt(String src, String salt) throws CryptoException {
        int saltLength = salt.getBytes().length;
        if (saltLength < 16) {
            StringBuilder saltBuilder = new StringBuilder(salt);
            for (int i = 0; i < 16 - saltLength; i++) {
                saltBuilder.append("0");
            }
            salt = saltBuilder.toString();
        }
        byte[] saltBytes = new byte[16];
        System.arraycopy(salt.getBytes(), 0, saltBytes, 0, saltBytes.length);
        return OpenBSDBCrypt.generate(src.toCharArray(), salt.getBytes(), COST);
    }
}
