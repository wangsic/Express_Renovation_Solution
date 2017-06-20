package com.jzwy.zkx.core.lambda.func;

/**
 *
 */
public interface FuncOfT1T2<T1, T2, R> {
    R invoke(T1 arg1, T2 arg2);
}
