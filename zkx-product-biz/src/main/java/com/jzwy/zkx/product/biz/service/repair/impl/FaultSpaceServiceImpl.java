package com.jzwy.zkx.product.biz.service.repair.impl;

import com.jzwy.zkx.core.assembler.DTODOAssembler;
import com.jzwy.zkx.core.service.contract.PagedResultsResponse;
import com.jzwy.zkx.core.service.contract.Response;
import com.jzwy.zkx.core.support.query.PagedResult;
import com.jzwy.zkx.product.biz.common.MessageConstants;
import com.jzwy.zkx.product.biz.domain.repair.FaultSpaceDO;
import com.jzwy.zkx.product.biz.manager.repair.FaultSpaceManager;
import com.jzwy.zkx.product.service.repair.FaultSpaceService;
import com.jzwy.zkx.product.service.repair.dto.FaultSpaceDTO;
import com.jzwy.zkx.product.service.repair.query.FaultSpaceQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 故障空间服务实现
 */
public class FaultSpaceServiceImpl implements FaultSpaceService {

    @Autowired
    private FaultSpaceManager faultSpaceManager;

    @Autowired
    private DTODOAssembler<FaultSpaceDTO, FaultSpaceDO> faultSpaceAssembler;

    @Override
    public Response<Void> add(FaultSpaceDTO faultSpace) throws Exception {
        FaultSpaceQuery faultSpaceQuery = new FaultSpaceQuery();
        faultSpaceQuery.setCode(faultSpace.getCode());
        boolean existing = this.faultSpaceManager.count(faultSpaceQuery) > 0;
        if (existing) {
            return Response.writeError(String.format(MessageConstants.FAULT_SPACE_HAS_BEEN_EXISTING_MESSAGE, faultSpace.getCode()));
        }

        FaultSpaceDO faultSpaceDO = faultSpaceAssembler.dtoToDo(faultSpace);
        this.faultSpaceManager.insert(faultSpaceDO);
        return Response.writeSuccess();
    }

    @Override
    public Response<Void> update(FaultSpaceDTO faultSpace) throws Exception {
        return null;
    }

    @Override
    public Response<Void> delete(Long id) throws Exception {
        return null;
    }

    @Override
    public Response<FaultSpaceDTO> get(Long id) throws Exception {
        FaultSpaceDO faultSpaceDO = this.faultSpaceManager.queryById(id);
        FaultSpaceDTO faultSpaceDTO = this.faultSpaceAssembler.doToDto(faultSpaceDO);
        return Response.writeSuccess(faultSpaceDTO);
    }

    @Override
    public PagedResultsResponse<FaultSpaceDTO> list(FaultSpaceQuery query) throws Exception {
        PagedResult<FaultSpaceDO> faultSpacePagedResults = this.faultSpaceManager.query(query);
        List<FaultSpaceDO> faultSpaceDOList = faultSpacePagedResults.getData();
        List<FaultSpaceDTO> faultSpaceDTOList = this.faultSpaceAssembler.doListToDtoList(faultSpaceDOList);
        return PagedResultsResponse.writeSuccess(faultSpaceDTOList, faultSpacePagedResults.toPagination());
    }
}
