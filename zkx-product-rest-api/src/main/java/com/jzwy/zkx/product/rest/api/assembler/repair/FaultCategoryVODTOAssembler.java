package com.jzwy.zkx.product.rest.api.assembler.repair;

import com.jzwy.zkx.core.assembler.VODTOAssembler;
import com.jzwy.zkx.product.rest.api.vo.FaultCategoryVO;
import com.jzwy.zkx.product.service.repair.dto.FaultCategoryDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * FaultCategoryVODTOAssembler
 */
public class FaultCategoryVODTOAssembler implements VODTOAssembler<FaultCategoryVO, FaultCategoryDTO> {

    @Override
    public FaultCategoryVO dtoToVo(FaultCategoryDTO dtoObject) throws Exception {
        if (null == dtoObject) {
            return null;
        }
        FaultCategoryVO voObject = new FaultCategoryVO();
        BeanUtils.copyProperties(dtoObject, voObject);
        return voObject;

    }

    @Override
    public FaultCategoryDTO voToDto(FaultCategoryVO voObject) throws Exception {
        if (null == voObject) {
            return null;
        }
        FaultCategoryDTO dtoObject = new FaultCategoryDTO();
        BeanUtils.copyProperties(voObject, dtoObject);
        return dtoObject;
    }

    @Override
    public List<FaultCategoryVO> dtoListToVoList(List<FaultCategoryDTO> dtoList) throws Exception {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Collections.EMPTY_LIST;
        }
        List<FaultCategoryVO> list = new ArrayList<>();
        for (FaultCategoryDTO dtoObject : dtoList) {
            list.add(dtoToVo(dtoObject));
        }
        return list;
    }

    @Override
    public List<FaultCategoryDTO> voListToDtoList(List<FaultCategoryVO> voList) throws Exception {
        if (CollectionUtils.isEmpty(voList)) {
            return Collections.EMPTY_LIST;
        }
        List<FaultCategoryDTO> list = new ArrayList<>();
        for (FaultCategoryVO voObject : voList) {
            list.add(voToDto(voObject));
        }
        return list;
    }
}
