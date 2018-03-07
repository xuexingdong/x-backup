package com.xuexingdong.x.common.utils;

import java.util.Random;

public final class XRandomUtils {

    private static final String RANDOM_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String nextString(int length) {
        return nextString(length, RANDOM_CHARS);
    }

    static String nextString(int length, String chars) {
        // FIXME
        int bound = RANDOM_CHARS.length();
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(bound));
        }
        return sb.toString();
    }
}
