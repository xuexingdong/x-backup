package com.xuexingdong.x.backend.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class DynamicLoginDTO {

    @NotEmpty(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号不合法")
    private String phone;

    @NotEmpty(message = "验证码不能为空")
    private String code;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
