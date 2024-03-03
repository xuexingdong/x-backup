package com.xxd.x.common.crypto;

public interface IrreversibleEncryption {
    String encrypt(String src) throws CryptoException;
}
