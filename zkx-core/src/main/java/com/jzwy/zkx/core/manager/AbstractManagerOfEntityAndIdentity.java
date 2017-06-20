package com.jzwy.zkx.core.manager;

import com.jzwy.zkx.core.dao.DaoOfEntityAndIdentity;
import com.jzwy.zkx.core.domain.Entity;
import com.jzwy.zkx.core.service.contract.BaseQuery;
import com.jzwy.zkx.core.support.query.PagedResult;

import java.io.Serializable;
import java.util.List;

/**
 * AbstractManagerOfEntityAndIdentity
 */
public abstract class AbstractManagerOfEntityAndIdentity<I extends Serializable, E extends Entity<I>, Q extends BaseQuery> implements ManagerOfEntityAndIdentity<I, E, Q> {

    private DaoOfEntityAndIdentity<I, E, Q> dao;

    protected AbstractManagerOfEntityAndIdentity(DaoOfEntityAndIdentity<I, E, Q> dao) {
        this.dao = dao;
    }

    @Override
    public I insert(E item) throws Exception {
        return this.dao.insert(item);
    }

    @Override
    public Integer update(E item) throws Exception {
        return this.dao.update(item);
    }

    @Override
    public E queryById(I id) throws Exception {
        return this.dao.queryById(id);
    }

    @Override
    public Integer deleteById(I id) throws Exception {
        return this.deleteById(id);
    }

    @Override
    public List<E> queryByIds(List<I> ids) throws Exception {
        return this.dao.queryByIds(ids);
    }

    @Override
    public Integer count(Q query) throws Exception {
        return this.dao.count(query);
    }

    @Override
    public PagedResult<E> query(Q query) throws Exception {
        return this.dao.query(query);
    }

    @Override
    public void insertAll(List<E> items) throws Exception {
        this.dao.insertAll(items);
    }

    @Override
    public Integer updateAll(List<E> items) throws Exception {
        return this.dao.updateAll(items);
    }
}
