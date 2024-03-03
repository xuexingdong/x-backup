package com.xxd.x.common.crypto;


public interface ReversibleEncryption {

    byte[] encrypt(byte[] content, byte[] secretKey) throws CryptoException;

    byte[] decrypt(byte[] content, byte[] secretKey) throws CryptoException;
}
