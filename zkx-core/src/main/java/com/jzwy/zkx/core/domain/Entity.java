package com.jzwy.zkx.core.domain;

import java.io.Serializable;

/**
 * 代表实体的接口
 */
public interface Entity<I extends Serializable> {

    /**
     *
     * @return
     */
    I getId();

    /**
     *
     * @param id
     */
    void setId(I id);
}
