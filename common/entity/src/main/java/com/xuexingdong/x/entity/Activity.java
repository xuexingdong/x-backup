package com.xxd.x.entity;

import com.xxd.x.entity.core.DeletedObject;
import com.xxd.x.entity.core.Identifiable;
import com.xxd.x.enumeration.AuditStatus;

import java.time.LocalDateTime;

public class Activity extends DeletedObject implements Identifiable<String> {
    private String id;
    private String name;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Override
    public String getId() {
        return id;
    }

    public Activity setId(String id) {
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
}
