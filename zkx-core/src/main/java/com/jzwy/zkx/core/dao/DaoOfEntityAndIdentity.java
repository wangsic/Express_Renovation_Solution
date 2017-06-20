package com.jzwy.zkx.core.dao;

import com.jzwy.zkx.core.domain.Entity;
import com.jzwy.zkx.core.service.contract.BaseQuery;
import com.jzwy.zkx.core.support.query.PagedResult;

import java.io.Serializable;
import java.util.List;

/**
 * Data access object 接口
 */
public interface DaoOfEntityAndIdentity<I extends Serializable, E extends Entity<I>, Q extends BaseQuery> {

    /**
     * 插入数据
     *
     * @param item
     * @return
     */
    I insert(E item);

    /**
     * 通过ID更新信息
     *
     * @param item
     * @return
     */
    Integer update(E item);

    /**
     * 通过ID获取信息
     *
     * @param id
     * @return
     */
    E queryById(I id);

    /**
     * 通过ID删除信息
     *
     * @param id
     * @return
     */
    Integer deleteById(I id);

    /**
     * 根据ID批量查询信息
     *
     * @param ids
     * @return
     */
    List<E> queryByIds(List<I> ids);

    /**
     * 统计满足查询条件的记录个数
     *
     * @param query
     * @return
     */
    Integer count(Q query);

    /**
     * 根据条件查询故障空间数据
     *
     * @param query
     * @return
     */
    PagedResult<E> query(Q query);

    /**
     * 批量插入信息
     *
     * @param items
     * @return
     */
    void insertAll(List<E> items);

    /**
     * 批量更新信息
     *
     * @param items
     * @return
     */
    Integer updateAll(List<E> items);

}
