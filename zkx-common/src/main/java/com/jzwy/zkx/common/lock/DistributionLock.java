package com.jzwy.zkx.common.lock;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 */
public interface DistributionLock {

    /**
     * 请求一个分布式锁, 如果没有请求到 则继续等待，直到获得锁
     * @param lockName 锁的名称
     * @param timeout  其他没有拿到锁的线程或请求等待锁的超时时间, 如果小于等于0则一直等待不超时直到获取到锁,, 大于0则一旦到达超时时间则不再等待立即退出
     * @param timeUnit
     */
    void lock(String lockName, int timeout, TimeUnit timeUnit);

    /**
     * 试图获取一个锁，一旦没有获取到锁则立即返回false, 得到锁则返回true
     * @param lockName 锁的名称
     * @Param lockExpireSeconds 锁自己本身的超时时间, 一旦系统出问题，该锁超时后可自行删除
     * @return
     */
    Boolean tryLock(String lockName, int lockExpireSeconds);

    /**
     *
     * @param lockName
     * @param timeout
     * @return
     */
    Boolean tryLock(String lockName, int timeout, TimeUnit timeUnit, int lockExpireSeconds);

    /**
     * 请求多个锁
     * @param lockNames
     * @param timeout
     * @return
     */
    Boolean tryLock(Collection<String> lockNames, int timeout, TimeUnit timeUnit);

    /**
     * 释放锁
     */
    void unlock(String lockName);

    /**
     * 释放多个锁
     */
    void unlock(Collection<String> lockNames);
}
