package com.jzwy.zkx.core.support.query;

/**
 * 分页接口
 */
public interface Pageable {

    /**
     * 获取页号
     *
     * @return
     */
    int getPageIndex();


    /**
     * 获取每页大小
     *
     * @return
     */
    int getPageSize();
}