package com.jzwy.zkx.core.lambda.func;

/**
 *
 */
public interface FuncOfT1T2T3<T1, T2, T3, R> {
    R invoke(T1 arg1, T2 arg2, T3 arg3);
}
