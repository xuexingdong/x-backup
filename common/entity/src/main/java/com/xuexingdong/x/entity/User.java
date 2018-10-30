package com.xuexingdong.x.entity;

import com.xuexingdong.x.entity.core.Identifiable;

import java.time.LocalDateTime;

public class User implements Identifiable<String> {

    private String id;
    private String avatar;
    private String username;
    private String remarkName;
    private transient String password;
    private transient String salt;
    private Integer points;
    private LocalDateTime createdAt;

    @Override
    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public User setRemarkName(String remarkName) {
        this.remarkName = remarkName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public User setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public Integer getPoints() {
        return points;
    }

    public User setPoints(Integer points) {
        this.points = points;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
