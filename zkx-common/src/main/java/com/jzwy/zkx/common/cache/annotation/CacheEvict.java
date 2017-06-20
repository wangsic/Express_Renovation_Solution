package com.jzwy.zkx.common.cache.annotation;

import java.lang.annotation.*;

/**
 * @CachEvict 的作用 主要针对方法配置，能够根据一定的条件对缓存进行清空
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheEvict {

    /**
     * 缓存的名称，在 spring 配置文件中定义，必须指定至少一个
     *
     * @return
     */
    String primaryProvider();

    /**
     * 二级缓存提供者
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
     * 缓存的 key，不可以为空，可按照 SpEL 表达式编写
     * 例如 key="'User:Name:' + #User.userName"
     *
     * @return
     */
    String key();

    /**
     * 缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才清空缓存
     * 例如 condition=”#User.userName.length()>2”
     *
     * @return
     */
    String condition() default "";

    /**
     * 是否清空所有缓存内容，缺省为 false，如果指定为 true，则方法调用后将立即清空所有缓存
     *
     * @return
     */
    boolean allEntries() default false;

    /**
     * 是否在方法执行前就清空，缺省为 false，如果指定为 true，则在方法还没有执行的时候就清空缓存，缺省情况下，如果方法执行抛出异常，则不会清空缓存
     *
     * @return
     */
    boolean beforeInvocation() default true;
}
