package com.jzwy.zkx.common.lock.redis;

import com.jzwy.zkx.common.lock.DistributionLock;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class RedisDistributionLock implements DistributionLock {

    private Logger logger = LogManager.getLogger(RedisDistributionLock.class);
    private JedisPool jedisPool;
    private static final int DEFAULT_LOCK_EXPIRE_TIME = 60;
    private static final String tryLockKeyValue = "n(*>^<*)n";

    public RedisDistributionLock(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 请求一个分布式锁, 如果没有请求到 则继续等待，直到获得锁
     *
     * @param lockName 锁的名称
     * @param timeout  其他没有拿到锁的线程或请求等待锁的超时时间, 如果小于等于0则一直等待不超时直到获取到锁,
     *                 大于0则一旦到达超时时间则不再等待立即退出
     * @param timeUnit
     */
    @Override
    public void lock(String lockName, int timeout, TimeUnit timeUnit) {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();

            Long currentTimeMillis = System.currentTimeMillis();
            Long timeoutMillis = timeUnit.toMillis(timeout); //超时时间

            while (timeout <= 0 || System.currentTimeMillis() - currentTimeMillis < timeoutMillis) {
                String expireMillisValue = String.valueOf(System.currentTimeMillis() + timeoutMillis + 1);  //锁等待的超时时间
                Long setResult = jedis.setnx(lockName, expireMillisValue);
                if (setResult == 1L) { //该线程请求锁成功
                    jedis.expire(lockName, DEFAULT_LOCK_EXPIRE_TIME);
                    logger.info("请求线程:" + Thread.currentThread().getId() + "获取到锁:" + lockName);
                    return;
                }

                //不设置超时时间就一直等待直到获取到锁
                if (timeout <= 0) {
                    Thread.sleep(200);
                    continue;
                }

                // 得到redis里存的过期时间
                String currentExpireMillisValue = jedis.get(lockName);
                if (!StringUtils.isEmpty(currentExpireMillisValue) && Long.parseLong(currentExpireMillisValue) < System.currentTimeMillis()) {
                    //获取上一个锁到期时间，并设置现在的锁到期时间
                    //只有一个线程才能获取上一个线上的设置时间, 因为jedis.getSet是原子操作串行同步的
                    String preExpireMillisValue = jedis.getSet(lockName, expireMillisValue);
                    if (!StringUtils.isEmpty(preExpireMillisValue) && preExpireMillisValue.equals(currentExpireMillisValue)) {
                        // 如果这个时候多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，才有权利获取锁
                        logger.info("请求线程:" + Thread.currentThread().getName() + "获取到锁:" + lockName);
                        return;
                    }
                }
                Thread.sleep(200);
            }
        } catch (Exception e) {
            logger.error("请求Redis分布式锁失败", e);
        } finally {
            this.close(jedis);
        }
    }

    /**
     * 如果获取锁则返回true，如果没有获取到锁则返回false而不继续等待
     *
     * @param lockName
     * @param lockExpireSeconds 锁本身的过期时间，默认为秒, 一旦过期, 该锁只要没释放就自己删除
     * @return
     */
    @Override
    public Boolean tryLock(String lockName, int lockExpireSeconds) {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            Long setResult = jedis.setnx(lockName, tryLockKeyValue);
            if (setResult == 1L) { //该线程请求锁成功
                jedis.expire(lockName, lockExpireSeconds);
                logger.info("请求线程:" + Thread.currentThread().getId() + "获取到锁:" + lockName);
                return true;
            }
        } catch (Exception e) {
            logger.error("请求Redis分布式锁失败", e);
        } finally {
            this.close(jedis);
        }
        return false;
    }

    /**
     * 如果获取锁则返回true，如果没有获取到锁则返回false而不继续等待
     *
     * @param lockName
     * @param timeout           其他线程等待超时的时间
     * @param timeUnit
     * @param lockExpireSeconds 锁本身的过期时间，默认为秒
     * @return
     */
    @Override
    public Boolean tryLock(String lockName, int timeout, TimeUnit timeUnit, int lockExpireSeconds) {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();

            Long currentTimeMillis = System.currentTimeMillis();
            Long timeoutMillis = timeUnit.toMillis(timeout);

            while (timeout <= 0 || System.currentTimeMillis() - currentTimeMillis < timeoutMillis) {
                String expireMillisValue = String.valueOf(System.currentTimeMillis() + timeoutMillis + 1);
                Long setResult = jedis.setnx(lockName, expireMillisValue);
                if (setResult == 1L) { //该线程请求锁成功
                    jedis.expire(lockName, lockExpireSeconds);
                    logger.info("请求线程:" + Thread.currentThread().getId() + "获取到锁:" + lockName);
                    return true;
                }

                //不设置超时时间就退出
                if (timeout <= 0) {
                    break;
                }

                // 得到redis里存的过期时间
                String currentExpireMillisValue = jedis.get(lockName);
                if (!StringUtils.isEmpty(currentExpireMillisValue) && Long.parseLong(currentExpireMillisValue) < System.currentTimeMillis()) {
                    //获取上一个锁到期时间，并设置现在的锁到期时间
                    //只有一个线程才能获取上一个线上的设置时间, 因为jedis.getSet是原子操作串行同步的
                    String preExpireMillisValue = jedis.getSet(lockName, expireMillisValue);
                    if (!StringUtils.isEmpty(preExpireMillisValue) && preExpireMillisValue.equals(currentExpireMillisValue)) {
                        // 如果这个时候多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，才有权利获取锁
                        logger.info("请求线程:" + Thread.currentThread().getId() + "获取到锁:" + lockName);
                        return true;
                    }
                }
                Thread.sleep(200);
            }
        } catch (Exception e) {
            logger.error("请求Redis分布式锁失败", e);
        } finally {
            this.close(jedis);
        }
        return false;
    }

    @Override
    public Boolean tryLock(Collection<String> lockNames, int timeout, TimeUnit timeUnit) {
        throw new NotImplementedException();
    }

    @Override
    public void unlock(String lockName) {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            jedis.del(lockName);
            logger.info("线程:" + Thread.currentThread().getId() + "释放锁:" + lockName);
        } catch (Exception e) {
            logger.error("释放Redis分布式锁失败", e);
        } finally {
            this.close(jedis);
        }
    }

    @Override
    public void unlock(Collection<String> lockNames) {
        if (lockNames == null || lockNames.isEmpty()) {
            return;
        }
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            String[] lockNameStrArray = new String[lockNames.size()];
            lockNameStrArray = lockNames.toArray(lockNameStrArray);
            jedis.del(lockNameStrArray);
            logger.info("线程:" + Thread.currentThread().getId() + "释放锁:" + lockNames);
        } catch (Exception e) {
            logger.error("释放Redis分布式锁失败, locknames=" + lockNames, e);
        } finally {
            this.close(jedis);
        }
    }

    public void close(Jedis jedis) {
        if (jedis == null) {
            return;
        }
        try {
            jedis.close();
        } catch (Exception e) {
            if (jedis.isConnected()) {
                jedis.quit();
                jedis.disconnect();
            }
        }
    }

}
