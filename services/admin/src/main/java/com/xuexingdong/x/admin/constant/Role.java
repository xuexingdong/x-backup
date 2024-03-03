package com.xxd.x.admin.constant;

public enum Role {
    ADMIN("ADMIN");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String value() {
        return role;
    }
}
