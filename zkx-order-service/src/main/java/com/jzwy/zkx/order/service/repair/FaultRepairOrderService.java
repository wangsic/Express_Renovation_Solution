package com.jzwy.zkx.order.service.repair;

import com.jzwy.zkx.order.service.repair.dto.FaultRepairOrderDTO;

import java.util.List;

/**
 * 故障订单服务
 */
public interface FaultRepairOrderService {

    /**
     * 创建故障维修订单
     *
     * @param faultRepairOrder
     * @throws Exception
     */
    void create(FaultRepairOrderDTO faultRepairOrder) throws Exception;

    /**
     * 更新故障维修订单
     *
     * @param faultRepairOrder
     * @throws Exception
     */
    void update(FaultRepairOrderDTO faultRepairOrder) throws Exception;

    /**
     * 审核故障维修订单
     *
     * @param orderId
     * @throws Exception
     */
    void audit(Long orderId) throws Exception;

    /**
     * 取消订单
     *
     * @param orderId
     * @throws Exception
     */
    void cancel(Long orderId) throws Exception;

    /**
     * 更新状态
     *
     * @param orderId
     * @throws Exception
     */
    void updateStatus(Long orderId, Integer status) throws Exception;

    /**
     * 根据查询条件查询列表
     * @return
     */
    List<FaultRepairOrderDTO> list();

    /**
     * 根据id查询详情
     * @param orderId
     * @return
     */
    FaultRepairOrderDTO detail(Long orderId);
}
