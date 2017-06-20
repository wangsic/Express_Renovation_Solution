package com.jzwy.zkx.core.assembler;

import com.jzwy.zkx.core.mapper.ObjectMapper;
import com.jzwy.zkx.core.reflect.ReflectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * AbstractDTODOAssembler
 */
public abstract class AbstractDTODOAssembler<T, D> implements DTODOAssembler<T, D> {

    private Class<T> dtoClazz;
    private Class<D> doClazz;

    public AbstractDTODOAssembler() {
        Class<?>[] classArray = ReflectionUtils.getSuperClassGenricTypes(this.getClass());
        dtoClazz = (Class<T>) classArray[0];
        doClazz = (Class<D>) classArray[1];
    }

    @Override
    public D dtoToDo(T dtoObject) throws Exception {
        if (null == dtoObject) {
            return null;
        }

        D doObject = doClazz.newInstance();
        BeanUtils.copyProperties(dtoObject, doObject);
        return doObject;
    }

    @Override
    public T doToDto(D doObject) throws Exception {
        if (null == doObject) {
            return null;
        }

        T dtoObject = dtoClazz.newInstance();
        BeanUtils.copyProperties(doObject, dtoObject);
        return dtoObject;
    }

    @Override
    public List<D> dtoListToDoList(List<T> dtoList) throws Exception {
        if (dtoList == null || dtoList.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<D> list = new ArrayList<>();
        for (T dtoObject : dtoList) {
            list.add(dtoToDo(dtoObject));
        }
        return list;
    }

    @Override
    public List<T> doListToDtoList(List<D> doList) throws Exception {
        if (doList == null || doList.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<T> list = new ArrayList<>();
        for (D doObject : doList) {
            list.add(doToDto(doObject));
        }
        return list;
    }
}
