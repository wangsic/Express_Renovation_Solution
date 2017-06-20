package com.jzwy.zkx.product.service.repair;

import com.jzwy.zkx.core.service.contract.PagedResultsResponse;
import com.jzwy.zkx.core.service.contract.Response;
import com.jzwy.zkx.product.service.repair.dto.FaultItemDTO;
import com.jzwy.zkx.product.service.repair.query.FaultItemQuery;

/**
 * 故障问题详情服务
 */
public interface FaultItemService {

    Response<Void> add(FaultItemDTO faultItem);

    Response<Void> update(FaultItemDTO faultItem);

    Response<Void> delete(Long id);

    Response<FaultItemDTO> get(Long id);

    PagedResultsResponse<FaultItemDTO> list(FaultItemQuery query);
}
