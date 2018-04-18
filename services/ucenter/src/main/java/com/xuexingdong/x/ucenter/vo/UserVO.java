package com.xuexingdong.x.ucenter.vo;

import com.xuexingdong.x.entity.User;

public class UserVO {

    private String id;

    private String username;

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

    public static UserVO fromModel(User user) {
        return new UserVO()
                .setId(user.getId())
                .setUsername(user.getUsername());
    }
}
