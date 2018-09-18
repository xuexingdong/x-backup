package com.xuexingdong.x.backend.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class PasswordLoginDTO {

    @NotEmpty(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号不合法")
    private String phone;

    @NotEmpty(message = "密码不能为空")
    @Pattern(regexp = "\\w{6,20}", message = "密码不合法")
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
