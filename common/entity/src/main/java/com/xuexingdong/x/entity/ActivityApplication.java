package com.xuexingdong.x.entity;

import com.xuexingdong.x.entity.core.DeletedObject;
import com.xuexingdong.x.entity.core.Identifiable;
import com.xuexingdong.x.enumeration.AuditStatus;

import java.time.LocalDateTime;

public class ActivityApplication extends DeletedObject implements Identifiable<Integer> {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private AuditStatus auditStatus;

    @Override
    public Integer getId() {
        return id;
    }

    public ActivityApplication setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ActivityApplication setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ActivityApplication setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public ActivityApplication setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public ActivityApplication setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    public AuditStatus getAuditStatus() {
        return auditStatus;
    }

    public ActivityApplication setAuditStatus(AuditStatus auditStatus) {
        this.auditStatus = auditStatus;
        return this;
    }
}
