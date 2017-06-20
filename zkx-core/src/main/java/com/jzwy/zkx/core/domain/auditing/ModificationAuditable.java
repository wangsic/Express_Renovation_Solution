package com.jzwy.zkx.core.domain.auditing;

import java.util.Date;

/**
 * 记录修改信息
 */
public interface ModificationAuditable {

    Long getLastModifierId();

    void setLastModifierId(Long lastModifierId);

    String getLastModifierName();

    void setLastModifierName(String lastModifierName);

    Date getLastModifiedTime();

    void setLastModifiedTime(Date lastModifiedTime);

    String getLastModifiedIP();

    void setLastModifiedIP(String lastModifiedIP);
}
