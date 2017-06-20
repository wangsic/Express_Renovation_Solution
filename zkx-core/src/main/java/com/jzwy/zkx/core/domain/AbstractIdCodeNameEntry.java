package com.jzwy.zkx.core.domain;

import java.io.Serializable;

/**
 * 记录Id, Code 和 Name 的入口
 */
public abstract class AbstractIdCodeNameEntry<I extends Serializable> {

    private I id;
    private String code;
    private String name;

    public I getId() {
        return id;
    }

    public void setId(I id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
