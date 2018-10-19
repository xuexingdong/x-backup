package com.xuexingdong.x.common.utils;

import java.util.UUID;

public final class XRandomUtils {

    public static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
