package com.xxd.x.common.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.Security;

public class AES implements SymmetricEncryption {

    private static final byte[] IV = new byte[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    @Override
    public byte[] encrypt(byte[] content, byte[] secretKey) throws CryptoException {
        return encrypt(content, secretKey, IV);
    }

    public byte[] encrypt(byte[] content, byte[] secretKey, byte[] iv) throws CryptoException {
        Security.addProvider(new BouncyCastleProvider());
        byte[] enc;
        try {
            Cipher in = Cipher.getInstance("AES/CBC/NoPadding");
            Key key = new SecretKeySpec(secretKey, "AES");
            in.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
            enc = in.doFinal(content);
        } catch (GeneralSecurityException e) {
            throw new CryptoException(e.getMessage());
        }
        return enc;
    }

    @Override
    public byte[] decrypt(byte[] content, byte[] secretKey) throws CryptoException {
        return decrypt(content, secretKey, IV);
    }

    public byte[] decrypt(byte[] content, byte[] secretKey, byte[] iv) throws CryptoException {
        Security.addProvider(new BouncyCastleProvider());
        byte[] dec;
        try {
            Key key = new SecretKeySpec(secretKey, "AES");
            Cipher out = Cipher.getInstance("AES/CBC/NoPadding");
            out.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            dec = out.doFinal(content);
        } catch (GeneralSecurityException e) {
            throw new CryptoException(e.getMessage());
        }
        return dec;
    }
}

