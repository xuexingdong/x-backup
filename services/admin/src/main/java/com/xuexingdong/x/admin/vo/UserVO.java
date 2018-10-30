package com.xuexingdong.x.admin.vo;

import java.time.LocalDateTime;

public class UserVO {
    private String id;
    private String username;
    private String avatar;
    private String remarkName;
    private int points;
    private LocalDateTime createdAt;

    public String getId() {
        return id;
    }

    public UserVO setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserVO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public UserVO setAvatar(String avatar) {
        this.avatar = avatar;
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
