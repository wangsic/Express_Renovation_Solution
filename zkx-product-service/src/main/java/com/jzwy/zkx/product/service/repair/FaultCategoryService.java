package com.jzwy.zkx.product.service.repair;

import com.jzwy.zkx.core.service.contract.PagedResultsResponse;
import com.jzwy.zkx.core.service.contract.Response;
import com.jzwy.zkx.product.service.repair.dto.FaultCategoryDTO;
import com.jzwy.zkx.product.service.repair.query.FaultCategoryQuery;


/**
 * Created by wangsic on 6/19/2017.
 */
public interface FaultCategoryService {

    Response<Void> add(FaultCategoryDTO faultCategory) throws Exception;

    Response<Void> update(FaultCategoryDTO faultCategory) throws Exception;

    Response<Void> delete(Long id) throws Exception;

    Response<FaultCategoryDTO> get(Long id) throws Exception;

    PagedResultsResponse<FaultCategoryDTO> list(FaultCategoryQuery query) throws Exception;
}
