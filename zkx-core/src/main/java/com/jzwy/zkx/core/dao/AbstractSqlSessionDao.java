package com.jzwy.zkx.core.dao;

import com.jzwy.zkx.core.domain.Entity;
import com.jzwy.zkx.core.reflect.ReflectionUtils;
import com.jzwy.zkx.core.service.contract.BaseQuery;
import com.jzwy.zkx.core.support.query.PagedResult;
import org.apache.commons.collections4.CollectionUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 抽象的基于Mybatis的SqlSessionDao
 */
public abstract class AbstractSqlSessionDao<I extends Serializable, E extends Entity<I>, Q extends BaseQuery> extends SqlSessionDaoSupport implements DaoOfEntityAndIdentity<I, E, Q> {

    protected final String NAMESPACE;

    public AbstractSqlSessionDao() {
        Class domainObjectClazz = ReflectionUtils.getSuperClassGenricType(this.getClass());
        String domainObjectClazzName = domainObjectClazz.getSimpleName();
        if (domainObjectClazzName.endsWith("DO")) {
            domainObjectClazzName = domainObjectClazzName.substring(0, domainObjectClazzName.length() - 2);
        }
        NAMESPACE = domainObjectClazzName + ".";
    }

    @Override
    public I insert(E item) {
        if (item == null) {
            return null;
        }

        getSqlSession().insert(NAMESPACE + "insert", item);
        return item.getId();
    }

    @Override
    public Integer update(E item) {
        if (item == null) {
            return 0;
        }
        return getSqlSession().update(NAMESPACE + "updateById", item);
    }

    @Override
    public E queryById(I id) {
        if (id == null) {
            return null;
        }
        E doObject = getSqlSession().selectOne(NAMESPACE + "queryById", id);
        return doObject;
    }

    @Override
    public Integer deleteById(I id) {
        if (id == null) {
            return 0;
        }
        return getSqlSession().update(NAMESPACE + "deleteById", id);
    }

    @Override
    public List<E> queryByIds(List<I> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<E> doObjectlist = getSqlSession().selectList(NAMESPACE + "queryByIds", ids);
        return doObjectlist;
    }

    @Override
    public Integer count(Q query) {
        if (query == null) {
            return 0;
        }
        Integer totalNum = getSqlSession().selectOne(NAMESPACE + "count", query);
        return totalNum;
    }

    @Override
    public PagedResult<E> query(Q query) {
        if (query == null) {
            return new PagedResult<>(null, null, 0, Collections.EMPTY_LIST);
        }
        Integer totalNum = count(query);
        List<E> list = getSqlSession().selectList(NAMESPACE + "query", query);
        return new PagedResult<>(query.getPageIndex(), query.getPageSize(), totalNum, list);
    }

    @Override
    public void insertAll(List<E> items) {
        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        getSqlSession().insert(NAMESPACE + "insertAll", items);
    }

    @Override
    public Integer updateAll(List<E> items) {
        if (CollectionUtils.isEmpty(items)) {
            return 0;
        }
        Integer rowNum = getSqlSession().update(NAMESPACE + "updateAll", items);
        return rowNum;
    }
}
