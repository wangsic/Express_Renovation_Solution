package com.jzwy.zkx.core.dao;

import com.jzwy.zkx.core.domain.Entity;
import com.jzwy.zkx.core.domain.auditing.FullAuditable;
import com.jzwy.zkx.core.service.contract.BaseQuery;

/**
 * BaseDao
 */
public abstract class BaseDao<E extends Entity<Long> & FullAuditable, Q extends BaseQuery> extends AbstractSqlSessionDao<Long, E, Q> {

}
