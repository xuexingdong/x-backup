package com.xxd.x.backend.vo;

import com.xxd.x.enumeration.AuditStatus;

import java.time.LocalDateTime;

public class ActivityApplicationVO {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private AuditStatus auditStatus;

    public Integer getId() {
        return id;
    }

    public ActivityApplicationVO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ActivityApplicationVO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ActivityApplicationVO setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public ActivityApplicationVO setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public ActivityApplicationVO setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    public AuditStatus getAuditStatus() {
        return auditStatus;
    }

    public ActivityApplicationVO setAuditStatus(AuditStatus auditStatus) {
        this.auditStatus = auditStatus;
        return this;
    }
}
