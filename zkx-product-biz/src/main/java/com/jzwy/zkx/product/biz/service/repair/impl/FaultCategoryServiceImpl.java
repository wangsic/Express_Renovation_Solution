package com.jzwy.zkx.product.biz.service.repair.impl;

import com.jzwy.zkx.core.assembler.DTODOAssembler;
import com.jzwy.zkx.core.service.contract.PagedResultsResponse;
import com.jzwy.zkx.core.service.contract.Response;
import com.jzwy.zkx.core.support.query.PagedResult;
import com.jzwy.zkx.product.biz.common.MessageConstants;
import com.jzwy.zkx.product.biz.domain.repair.FaultCategoryDO;
import com.jzwy.zkx.product.biz.manager.repair.FaultCategoryManager;
import com.jzwy.zkx.product.service.repair.FaultCategoryService;
import com.jzwy.zkx.product.service.repair.dto.FaultCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.jzwy.zkx.product.service.repair.query.FaultCategoryQuery;

import java.util.List;


/**
 * 故障类别服务实现
 */
public class FaultCategoryServiceImpl implements FaultCategoryService {

    @Autowired
    private FaultCategoryManager faultCategoryManager;

    @Autowired
    private DTODOAssembler<FaultCategoryDTO, FaultCategoryDO> FaultCategoryAssembler;

    @Override
    public Response<Void> add(FaultCategoryDTO faultCategory) throws Exception {
        FaultCategoryQuery faultCategoryQuery = new FaultCategoryQuery();
        faultCategoryQuery.setCode(faultCategory.getCode());
        boolean existing = this.faultCategoryManager.count(faultCategoryQuery) > 0;
        if (existing) {
            return Response.writeError(String.format(MessageConstants.FAULT_CATEGORY_HAS_BEEN_EXISTING_MESSAGE, faultCategory.getCode()));
        }
        FaultCategoryDO faultCategoryDO = FaultCategoryAssembler.dtoToDo(faultCategory);
        this.faultCategoryManager.insert(faultCategoryDO);
        return Response.writeSuccess();
    }

    @Override
    public Response<Void> update(FaultCategoryDTO faultCategory) throws Exception {
        return null;
    }

    @Override
    public Response<Void> delete(Long id) throws Exception {
        return null;
    }

    @Override
    public Response<FaultCategoryDTO> get(Long id) throws Exception {
        FaultCategoryDO faultCategoryDO = this.faultCategoryManager.queryById(id);
        FaultCategoryDTO faultCategoryDTO = this.FaultCategoryAssembler.doToDto(faultCategoryDO);
        return Response.writeSuccess(faultCategoryDTO);
    }

    @Override
    public PagedResultsResponse<FaultCategoryDTO> list(FaultCategoryQuery query) throws Exception {
        PagedResult<FaultCategoryDO> faultCategoryDOPagedResults = this.faultCategoryManager.query(query);
        List<FaultCategoryDO> faultCategoryDOList = faultCategoryDOPagedResults.getData();
        List<FaultCategoryDTO> faultCategoryDTOLis = this.FaultCategoryAssembler.doListToDtoList(faultCategoryDOList);
        return PagedResultsResponse.writeSuccess(faultCategoryDTOLis, faultCategoryDOPagedResults.toPagination());
    }
}
