package com.xuexingdong.x.enumeration;

public enum ActivityStatus {
    REJECTED(-1),
    WAITING(0),
    ACCEPT(1);

    private int status;

    ActivityStatus(int status) {
        this.status = status;
    }
}
