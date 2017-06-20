package com.jzwy.zkx.core.reflect.invoker;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Set Field调用器
 */
public class SetFieldInvoker implements Invoker {
    private Field field;

    public SetFieldInvoker(Field field) {
        this.field = field;
    }

    public Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException {
        field.set(target, args[0]);
        return null;
    }

    public Class<?> getType() {
        return field.getType();
    }
}