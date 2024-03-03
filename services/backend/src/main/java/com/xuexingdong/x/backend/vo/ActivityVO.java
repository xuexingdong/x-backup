package com.xxd.x.backend.vo;

import com.xxd.x.entity.core.DeletedObject;
import com.xxd.x.enumeration.AuditStatus;

import java.time.LocalDateTime;

public class ActivityVO extends DeletedObject {
    private String id;
    private String name;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private AuditStatus auditStatus;

    public String getId() {
        return id;
    }

    public ActivityVO setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ActivityVO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ActivityVO setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public ActivityVO setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public ActivityVO setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    public AuditStatus getAuditStatus() {
        return auditStatus;
    }

    public ActivityVO setAuditStatus(AuditStatus auditStatus) {
        this.auditStatus = auditStatus;
        return this;
    }
}
