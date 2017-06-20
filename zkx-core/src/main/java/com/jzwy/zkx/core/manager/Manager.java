package com.jzwy.zkx.core.manager;

import com.jzwy.zkx.core.domain.Entity;
import com.jzwy.zkx.core.domain.auditing.FullAuditable;
import com.jzwy.zkx.core.service.contract.BaseQuery;

/**
 * Manager接口
 */
public interface Manager<E extends Entity<Long> & FullAuditable, Q extends BaseQuery> extends ManagerOfEntityAndIdentity<Long, E, Q> {

}
