package com.jzwy.zkx.core.assembler;

import java.util.List;

/**
 * DTO和DO转换接口
 */
public interface DTODOAssembler<T, D> {
    D dtoToDo(T dtoObject) throws Exception;

    T doToDto(D doObject) throws Exception;

    List<D> dtoListToDoList(List<T> dtoList) throws Exception;

    List<T> doListToDtoList(List<D> doList) throws Exception;
}
