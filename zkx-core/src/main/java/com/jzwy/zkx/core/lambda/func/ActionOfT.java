package com.jzwy.zkx.core.lambda.func;

/**
 *
 */
public interface ActionOfT<T> {
    void invoke(T arg);
}
