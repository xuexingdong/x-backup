package com.xxd.x.common.crypto;

public interface EncryptionWithSalt extends Encryption {
    public String encrypt(String src, String salt) throws CryptoException;
}
