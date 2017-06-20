package com.jzwy.zkx.product.service.repair.dto;

import com.jzwy.zkx.core.service.contract.BaseDTO;

/**
 * 故障种类DTO
 */
public class FaultCategoryDTO extends BaseDTO {
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
