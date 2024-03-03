package com.xxd.x.chatbot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "location")
public class Location {
    @Id
    private String id;
    // 经度
    private double longitude;
    // 纬度
    private double latitude;
    @Field("created_at")
    private LocalDateTime createdAt;

    public String getId() {
        return id;
    }

    public Location setId(String id) {
        this.id = id;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Location setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public Location setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Location setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}