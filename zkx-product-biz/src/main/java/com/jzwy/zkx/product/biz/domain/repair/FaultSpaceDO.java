package com.jzwy.zkx.product.biz.domain.repair;

import com.jzwy.zkx.core.domain.BaseDO;

/**
 * 故障空间领域对象
 */
public class FaultSpaceDO extends BaseDO {

    private String code;
    private String name;

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
