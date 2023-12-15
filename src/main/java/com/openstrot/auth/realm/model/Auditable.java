package com.openstrot.auth.realm.model;

import org.springframework.data.mongodb.core.mapping.Field;

public abstract class Auditable {

    @Field("created_by")
    private String createdBy;

    @Field("created_at")
    private long createdAt;

    @Field("last_modified_by")
    private String lastModifiedBy;

    @Field("last_modified_at")
    private long lastModifiedAt;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public long getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(long lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
}
