package com.jzwy.zkx.core.manager;

import com.jzwy.zkx.core.dao.Dao;
import com.jzwy.zkx.core.domain.Entity;
import com.jzwy.zkx.core.domain.auditing.FullAuditable;
import com.jzwy.zkx.core.service.contract.BaseQuery;

/**
 * BaseManager
 */
public abstract class BaseManager<E extends Entity<Long> & FullAuditable, Q extends BaseQuery> extends AbstractManagerOfEntityAndIdentity<Long, E, Q> {

    public BaseManager(Dao<E, Q> dao) {
        super(dao);
    }
}
