package com.jzwy.zkx.core.dao;

import com.jzwy.zkx.core.domain.Entity;
import com.jzwy.zkx.core.domain.auditing.FullAuditable;
import com.jzwy.zkx.core.service.contract.BaseQuery;

/**
 * 指定Id泛型类型为Long的Dao接口
 */
public interface Dao<E extends Entity<Long> & FullAuditable, Q extends BaseQuery> extends DaoOfEntityAndIdentity<Long, E, Q> {
}
