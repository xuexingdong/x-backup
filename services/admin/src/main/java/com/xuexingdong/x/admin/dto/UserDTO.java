package com.xxd.x.admin.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class UserDTO {

    @NotEmpty
    private String username;

    @NotEmpty
    @Length(min = 6, message = "{register.password.minlength}")
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
