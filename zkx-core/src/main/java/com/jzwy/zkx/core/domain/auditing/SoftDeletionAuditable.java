package com.jzwy.zkx.core.domain.auditing;

/**
 * 记录删除信息
 */
public interface SoftDeletionAuditable {

    Integer getIsDeleted();

    void setIsDeleted(Integer isDeleted);
}
