package com.jzwy.zkx.common.cache.annotation;

import java.lang.annotation.*;

/**
 * @Cacheable 的作用 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cacheable {
    /**
     * 缓存提供器的名称，在 spring 配置文件中定义，必须指定至少一个
     *
     * @return
     */
    String primaryProvider();

    /**
     * 二级缓存提供者, 可不设置
     *
     * @return
     */
    String secondLevelProvider() default "";

    /**
     * 缓存名称
     *
     * @return
     */
    String cacheName() default "";

    /**
     * 缓存的 key，不能为空，可要按照 SpEL 表达式编写
     *
     * @return
     */
    String key();

    /**
     * 缓存过期时间长度，单位秒
     *
     * @return
     */
    int expiration() default 0;
}
