package com.jzwy.zkx.task.biz.domain.remake;

/**
 * 改造预约订单数据表对象
 */
public class RemakeOrderDO {

    private String orderNo;
    private Long storeId;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
