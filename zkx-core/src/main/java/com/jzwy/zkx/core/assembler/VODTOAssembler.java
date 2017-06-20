package com.jzwy.zkx.core.assembler;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * VO和DTO转换接口
 */
public interface VODTOAssembler<V, T> {

    V dtoToVo(T dtoObject) throws Exception;

    T voToDto(V voObject) throws Exception;

    List<V> dtoListToVoList(List<T> dtoList) throws Exception;

    List<T> voListToDtoList(List<V> voList) throws Exception;
}
