package com.jzwy.zkx.product.service.repair.query;

import com.jzwy.zkx.core.service.contract.BaseQuery;

/**
 * 故障类别查询
 */
public class FaultCategoryQuery extends BaseQuery {

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
