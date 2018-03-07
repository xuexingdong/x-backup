package com.xuexingdong.x.common.utils;

import java.time.Clock;

public final class XDateTimeUtils {

    public static long get10Timestamp() {
        return Math.floorDiv(Clock.systemDefaultZone().millis(), 1000L);
    }

    public static long get13Timestamp() {
        return Clock.systemDefaultZone().millis();
    }

    public static String get10TimestampStr() {
        return String.valueOf(get10Timestamp());
    }

    public static String get13TimestampStr() {
        return String.valueOf(get13Timestamp());
    }

}
