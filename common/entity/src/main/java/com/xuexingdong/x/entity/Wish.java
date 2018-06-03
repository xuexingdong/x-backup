package com.xuexingdong.x.entity;

public class Wish {
    private String id;
    private String userId;
    private String title;
    private String content;
    private String pic;

    public String getId() {
        return id;
    }

    public Wish setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Wish setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Wish setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Wish setContent(String content) {
        this.content = content;
        return this;
    }

    public String getPic() {
        return pic;
    }

    public Wish setPic(String pic) {
        this.pic = pic;
        return this;
    }
}
