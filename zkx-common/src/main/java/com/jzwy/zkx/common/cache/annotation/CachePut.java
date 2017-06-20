package com.jzwy.zkx.common.cache.annotation;

import java.lang.annotation.*;

/**
 * @CachePut 的作用 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存，和 @Cacheable 不同的是，它每次都会触发真实方法的调用
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CachePut {

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
     * @return
     */
    String key();
}
