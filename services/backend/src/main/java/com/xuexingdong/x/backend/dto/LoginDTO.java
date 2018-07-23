package com.xuexingdong.x.backend.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class LoginDTO {

    @NotEmpty
    @Length(max = 20, message = "{login.username.invalid}")
    private String username;

    @NotEmpty
    @Length(min = 6, max = 20, message = "{login.password.invalid}")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
