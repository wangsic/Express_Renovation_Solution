package com.jzwy.zkx.product.service.repair.dto;

import com.jzwy.zkx.core.service.contract.BaseDTO;

/**
 * 故障空间种类关联DTO
 */
public class FaultSpaceCategoryDTO extends BaseDTO {
    private Long faultSpaceId;
    private String faultSpaceCode;
    private String faultSpaceName;
    private Long faultCategoryId;
    private String faultCategoryCode;
    private String faultCategoryName;

    public Long getFaultSpaceId() {
        return faultSpaceId;
    }

    public void setFaultSpaceId(Long faultSpaceId) {
        this.faultSpaceId = faultSpaceId;
    }

    public String getFaultSpaceCode() {
        return faultSpaceCode;
    }

    public void setFaultSpaceCode(String faultSpaceCode) {
        this.faultSpaceCode = faultSpaceCode;
    }

    public String getFaultSpaceName() {
        return faultSpaceName;
    }

    public void setFaultSpaceName(String faultSpaceName) {
        this.faultSpaceName = faultSpaceName;
    }

    public Long getFaultCategoryId() {
        return faultCategoryId;
    }

    public void setFaultCategoryId(Long faultCategoryId) {
        this.faultCategoryId = faultCategoryId;
    }

    public String getFaultCategoryCode() {
        return faultCategoryCode;
    }

    public void setFaultCategoryCode(String faultCategoryCode) {
        this.faultCategoryCode = faultCategoryCode;
    }

    public String getFaultCategoryName() {
        return faultCategoryName;
    }

    public void setFaultCategoryName(String faultCategoryName) {
        this.faultCategoryName = faultCategoryName;
    }
}
