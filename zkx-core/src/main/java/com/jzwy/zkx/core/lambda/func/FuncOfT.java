package com.jzwy.zkx.core.lambda.func;

/**
 *
 */
public interface FuncOfT<T, R> {
    R invoke(T arg);
}
