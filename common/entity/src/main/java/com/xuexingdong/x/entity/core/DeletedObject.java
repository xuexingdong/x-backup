package com.xuexingdong.x.entity.core;

import java.time.LocalDateTime;

public class DeletedObject extends BasicObject {

    protected Boolean deleted;
    protected LocalDateTime deletedAt;
    protected String deletedBy;
    protected String deletedToken;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getDeletedToken() {
        return deletedToken;
    }

    public void setDeletedToken(String deletedToken) {
        this.deletedToken = deletedToken;
    }
}
