package com.xuexingdong.x.chatbot.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chat")
public class Chat {
    private String id;
    private String msgid;
    private String fromUsername;
    private String toUsername;
    private LocalDateTime createTime;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
