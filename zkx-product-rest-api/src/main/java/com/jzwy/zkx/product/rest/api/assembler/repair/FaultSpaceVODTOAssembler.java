package com.jzwy.zkx.product.rest.api.assembler.repair;

import com.jzwy.zkx.common.util.DateTimeUtils;
import com.jzwy.zkx.core.assembler.VODTOAssembler;
import com.jzwy.zkx.product.rest.api.vo.FaultSpaceVO;
import com.jzwy.zkx.product.service.repair.dto.FaultSpaceDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * FaultSpaceVODTOAssembler
 */
@Component
public class FaultSpaceVODTOAssembler implements VODTOAssembler<FaultSpaceVO, FaultSpaceDTO> {
    @Override
    public FaultSpaceVO dtoToVo(FaultSpaceDTO dtoObject) throws Exception {
        if (null == dtoObject) {
            return null;
        }
        FaultSpaceVO voObject = new FaultSpaceVO();
        BeanUtils.copyProperties(dtoObject, voObject);
        voObject.setCreatedTime(DateTimeUtils.toDateTimeString(dtoObject.getCreatedTime(), DateTimeUtils.TIMEFORMAT_STANDARD));
        voObject.setLastModifiedTime(DateTimeUtils.toDateTimeString(dtoObject.getLastModifiedTime(), DateTimeUtils.TIMEFORMAT_STANDARD));
        return voObject;
    }

    @Override
    public FaultSpaceDTO voToDto(FaultSpaceVO voObject) throws Exception {
        if (null == voObject) {
            return null;
        }
        FaultSpaceDTO dtoObject = new FaultSpaceDTO();
        BeanUtils.copyProperties(voObject, dtoObject);
        return dtoObject;
    }

    @Override
    public List<FaultSpaceVO> dtoListToVoList(List<FaultSpaceDTO> dtoList) throws Exception {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Collections.EMPTY_LIST;
        }
        List<FaultSpaceVO> list = new ArrayList<>();
        for (FaultSpaceDTO dtoObject : dtoList) {
            list.add(dtoToVo(dtoObject));
        }
        return list;
    }

    @Override
    public List<FaultSpaceDTO> voListToDtoList(List<FaultSpaceVO> voList) throws Exception {
        if (CollectionUtils.isEmpty(voList)) {
            return Collections.EMPTY_LIST;
        }
        List<FaultSpaceDTO> list = new ArrayList<>();
        for (FaultSpaceVO voObject : voList) {
            list.add(voToDto(voObject));
        }
        return list;
    }
}
