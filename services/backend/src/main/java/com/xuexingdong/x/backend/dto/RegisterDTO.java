package com.xuexingdong.x.backend.dto;

import javax.validation.constraints.NotEmpty;

public class RegisterDTO extends LoginDTO {

    @NotEmpty(message = "{register.chatId.notEmpty}")
    private String chatId;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
