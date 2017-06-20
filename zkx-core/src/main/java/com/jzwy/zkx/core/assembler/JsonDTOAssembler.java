package com.jzwy.zkx.core.assembler;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Json和DTO转换接口
 */
public interface JsonDTOAssembler<T> {

    T jsonToDto(JSONObject json) throws Exception;

    JSONObject dtoToJson(T dtoObject) throws Exception;

    JSONObject dtoListToJson(List<T> dtoObjectList) throws Exception;
}
