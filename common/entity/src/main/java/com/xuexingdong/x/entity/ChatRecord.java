package com.xuexingdong.x.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "chat_record")
public class ChatRecord {
    @Id
    private String id;
    private String msgid;
    @Field("from_username")
    private String fromUsername;
    @Field("to_username")
    private String toUsername;
    @Field("created_at")
    private LocalDateTime createdAt;
    private String content;

    public String getId() {
        return id;
    }

    public ChatRecord setId(String id) {
        this.id = id;
        return this;
    }

    public String getMsgid() {
        return msgid;
    }

    public ChatRecord setMsgid(String msgid) {
        this.msgid = msgid;
        return this;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public ChatRecord setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
        return this;
    }

    public String getToUsername() {
        return toUsername;
    }

    public ChatRecord setToUsername(String toUsername) {
        this.toUsername = toUsername;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ChatRecord setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ChatRecord setContent(String content) {
        this.content = content;
        return this;
    }
}