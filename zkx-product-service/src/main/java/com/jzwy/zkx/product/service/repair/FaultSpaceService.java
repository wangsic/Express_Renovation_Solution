package com.jzwy.zkx.product.service.repair;

import com.jzwy.zkx.core.service.contract.PagedResultsResponse;
import com.jzwy.zkx.core.service.contract.Response;
import com.jzwy.zkx.product.service.repair.dto.FaultSpaceDTO;
import com.jzwy.zkx.product.service.repair.query.FaultSpaceQuery;

/**
 * 故障空间维护服务
 */
public interface FaultSpaceService {

    Response<Void> add(FaultSpaceDTO faultSpace) throws Exception;

    Response<Void> update(FaultSpaceDTO faultSpace) throws Exception;

    Response<Void> delete(Long id) throws Exception;

    Response<FaultSpaceDTO> get(Long id) throws Exception;

    PagedResultsResponse<FaultSpaceDTO> list(FaultSpaceQuery query) throws Exception;
}
