package com.jzwy.zkx.core.domain;

import com.jzwy.zkx.core.domain.auditing.FullAuditable;

import java.io.Serializable;
import java.util.Date;

/**
 * 抽象的可审计的实体， 记录新增时间，修改时间，新增人，修改人，操作人IP， 是否删除等
 */
public abstract class AbstractAuditableEntity<I extends Serializable> extends AbstractEntity<I> implements FullAuditable {

    private I id;
    private long creatorId;
    private String creatorName;
    private Date createdTime;
    private long lastModifierId;
    private String lastModifierName;
    private Date lastModifiedTime;
    private String lastModifiedIP;
    private Integer isDeleted;

    @Override
    public I getId() {
        return id;
    }

    @Override
    public void setId(I id) {
        this.id = id;
    }

    @Override
    public long getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public String getCreatorName() {
        return creatorName;
    }

    @Override
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Override
    public Date getCreatedTime() {
        return createdTime;
    }

    @Override
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public Long getLastModifierId() {
        return lastModifierId;
    }

    @Override
    public void setLastModifierId(Long lastModifierId) {
        this.lastModifierId = lastModifierId;
    }

    @Override
    public String getLastModifierName() {
        return lastModifierName;
    }

    @Override
    public void setLastModifierName(String lastModifierName) {
        this.lastModifierName = lastModifierName;
    }

    @Override
    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    @Override
    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    @Override
    public String getLastModifiedIP() {
        return lastModifiedIP;
    }

    @Override
    public void setLastModifiedIP(String lastModifiedIP) {
        this.lastModifiedIP = lastModifiedIP;
    }

    @Override
    public Integer getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

}
