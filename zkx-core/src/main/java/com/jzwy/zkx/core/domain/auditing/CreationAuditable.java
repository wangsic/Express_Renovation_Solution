package com.jzwy.zkx.core.domain.auditing;

import java.util.Date;

/**
 * 记录创建信息
 */
public interface CreationAuditable {

    long getCreatorId();

    void setCreatorId(Long creatorId);

    String getCreatorName();

    void setCreatorName(String creatorName);

    Date getCreatedTime();

    void setCreatedTime(Date createdTime);
}
