package com.jzwy.zkx.core.service.contract;

import java.util.Date;

/**
 * 抽象的DTO
 */
public abstract class BaseDTO {

    private Long id;
    private long creatorId;
    private String creatorName;
    private Date createdTime;
    private long lastModifierId;
    private String lastModifierName;
    private Date lastModifiedTime;
    private String lastModifiedIP;
    private Integer isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public long getLastModifierId() {
        return lastModifierId;
    }

    public void setLastModifierId(long lastModifierId) {
        this.lastModifierId = lastModifierId;
    }

    public String getLastModifierName() {
        return lastModifierName;
    }

    public void setLastModifierName(String lastModifierName) {
        this.lastModifierName = lastModifierName;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getLastModifiedIP() {
        return lastModifiedIP;
    }

    public void setLastModifiedIP(String lastModifiedIP) {
        this.lastModifiedIP = lastModifiedIP;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
