package com.xxd.x.backend.vo;

import java.time.LocalDateTime;

public class UserVO {
    private String id;
    private String openid;
    private int points;
    private String remarkName;
    private LocalDateTime createdAt;

    public String getId() {
        return id;
    }

    public UserVO setId(String id) {
        this.id = id;
        return this;
    }

    public String getOpenid() {
        return openid;
    }

    public UserVO setOpenid(String openid) {
        this.openid = openid;
        return this;
    }

    public int getPoints() {
        return points;
    }

    public UserVO setPoints(int points) {
        this.points = points;
        return this;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public UserVO setRemarkName(String remarkName) {
        this.remarkName = remarkName;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UserVO setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
