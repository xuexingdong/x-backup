package com.xuexingdong.x.entity;

import com.xuexingdong.x.entity.core.DeletedObject;
import com.xuexingdong.x.enumeration.ActivityStatus;

import java.time.LocalDateTime;

public class Activity extends DeletedObject {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private ActivityStatus status;

    public Integer getId() {
        return id;
    }

    public Activity setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Activity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Activity setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public Activity setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public Activity setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public Activity setStatus(ActivityStatus status) {
        this.status = status;
        return this;
    }
}
