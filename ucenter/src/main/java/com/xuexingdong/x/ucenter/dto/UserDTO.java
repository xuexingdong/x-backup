package com.xuexingdong.x.ucenter.dto;

import javax.validation.constraints.NotEmpty;

public class UserDTO {

    @NotEmpty(message = "{username.notEmpty}")
    private String username;

    @NotEmpty(message = "{password.notEmpty}")
    private String password;

    public String getUsername() {
        return username;
    }

    public UserDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}
