package com.jzwy.zkx.product.service.repair.query;

import com.jzwy.zkx.core.service.contract.BaseQuery;

/**
 * 故障问题详情查询参数
 */
public class FaultItemQuery extends BaseQuery {

    private String code;
    private String name;
    private Long faultSpaceId;
    private Long faultCategoryId;

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

    public Long getFaultSpaceId() {
        return faultSpaceId;
    }

    public void setFaultSpaceId(Long faultSpaceId) {
        this.faultSpaceId = faultSpaceId;
    }

    public Long getFaultCategoryId() {
        return faultCategoryId;
    }

    public void setFaultCategoryId(Long faultCategoryId) {
        this.faultCategoryId = faultCategoryId;
    }
}
