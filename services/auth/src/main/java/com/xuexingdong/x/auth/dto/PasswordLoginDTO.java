package com.xuexingdong.x.auth.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class PasswordLoginDTO {

    @NotEmpty(message = "用户名")
    @Pattern(regexp = "\\w{6,20}", message = "用户名不合法")
    private String username;

    @NotEmpty(message = "密码不能为空")
    @Pattern(regexp = "\\w{6,20}", message = "密码不合法")
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
