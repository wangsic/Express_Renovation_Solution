package com.jzwy.zkx.common.cache.redis;

import com.jzwy.zkx.common.cache.CacheKeyGenerator;
import com.jzwy.zkx.common.cache.CacheObjectGenerator;
import com.jzwy.zkx.common.serialization.ObjectSerializer;
import com.jzwy.zkx.common.util.StringUtil;
import com.jzwy.zkx.core.exception.Guard;
import com.jzwy.zkx.core.reflect.MetaClassReflector;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.lang.reflect.Field;
import java.util.*;

/**
 * RedisCacheProviderImpl
 */
public class RedisCacheProviderImpl implements RedisCacheProvider {

    private JedisPool jedisPool;

    private ObjectSerializer objectSerializer;

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void setObjectSerializer(ObjectSerializer objectSerializer) {
        this.objectSerializer = objectSerializer;
    }

    @Override
    public void set(String cacheName, String key, Object value) {
        set(cacheName, key, value, 0);
    }

    @Override
    public void set(String cacheName, String key, Object value, Integer expireSeconds) {
        Guard.notNullOrEmpty(key, "key");
        Guard.notNull(value, "value");

        String combinedKey = this.getCombinedKey(cacheName, key);
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            jedis.set(combinedKey.getBytes(), objectSerializer.serialize(value));
            if (expireSeconds != null && expireSeconds > 0) {
                jedis.expire(combinedKey, expireSeconds);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeJedis(jedis);
        }
    }

    @Override
    public <T> T get(String cacheName, String key, Class<T> clazz) {
        Guard.notNullOrEmpty(key, "key");

        T objectValue;
        Jedis jedis = null;
        String combinedKey = this.getCombinedKey(cacheName, key);
        try {
            jedis = this.jedisPool.getResource();
            objectValue = this.objectSerializer.deserialize(jedis.get(combinedKey.getBytes()), clazz);
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeJedis(jedis);
        }
        return objectValue;
    }

    @Override
    public Object get(String cacheName, String key) {
        Guard.notNullOrEmpty(key, "key");

        Jedis jedis = null;
        String combinedKey = this.getCombinedKey(cacheName, key);
        try {
            jedis = this.jedisPool.getResource();
            return this.objectSerializer.deserialize(jedis.get(combinedKey.getBytes()), Object.class);
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeJedis(jedis);
        }
    }

    @Override
    public void remove(String cacheName, String key) {
        Jedis jedis = null;
        String combinedKey = this.getCombinedKey(cacheName, key);
        try {
            jedis = this.jedisPool.getResource();
            jedis.del(combinedKey);
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeJedis(jedis);
        }
    }

    @Override
    public boolean exists(String cacheName, String key) {
        String combinedKey = this.getCombinedKey(cacheName, key);
        Jedis jedis = this.jedisPool.getResource();
        boolean existingKey = jedis.exists(key.getBytes());
        this.closeJedis(jedis);
        return existingKey;
    }

    @Override
    public <T> void setList(String key, Collection<T> values) {
        setList(key, values, 0);
    }

    @Override
    public <T> void setList(String key, Collection<T> values, Integer expireSeconds) {
        Guard.notNull(key, "key");
        Guard.notNull(values, "values");
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();

            byte[] keyBytes = key.getBytes();
            long index = 0;
            Iterator<T> iterator = values.iterator();
            while (iterator.hasNext()) {
                T item = iterator.next();
                pipeline.lset(keyBytes, index, objectSerializer.serialize(item));
                index++;
            }
            if (expireSeconds != null && expireSeconds > 0) {
                pipeline.expire(keyBytes, expireSeconds);
            }
            pipeline.sync();
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeJedis(jedis);
        }
    }

    @Override
    public <T> Collection<T> getList(String key, Class<T> clazz) {
        List<T> valueList = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            List<byte[]> byteList = jedis.lrange(key.getBytes(), 0, -1);
            if (byteList != null) {
                for (byte[] bytes : byteList) {
                    valueList.add(this.objectSerializer.deserialize(bytes, clazz));
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeJedis(jedis);
        }
        return valueList;
    }

    @Override
    public void setHash(String hashId, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            jedis.hset(hashId, field, value);
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeJedis(jedis);
        }
    }

    @Override
    public void setHash(Map<String, Map<String, String>> hashFieldValueMap) {
        Guard.notNull(hashFieldValueMap, "hashFieldValueMap");
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();
            for (Map.Entry<String, Map<String, String>> hashFieldValueEntry : hashFieldValueMap.entrySet()) {
                Map<String, String> fieldValueMap = hashFieldValueEntry.getValue();
                if (fieldValueMap != null) {
                    for (Map.Entry<String, String> fieldValueEntry : fieldValueMap.entrySet()) {
                        pipeline.hset(hashFieldValueEntry.getKey(), fieldValueEntry.getKey(), fieldValueEntry.getValue());
                    }
                }
            }
            pipeline.sync();
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeJedis(jedis);
        }
    }

    @Override
    public void setHash(String hashId, Object value) {
        Guard.notNull(hashId, "hashId");
        Guard.notNull(value, "value");

        MetaClassReflector metaClassReflector = MetaClassReflector.forClass(value.getClass());
        Field[] fields = metaClassReflector.getFields();

        String fieldName;
        Object fieldValue;
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            for (Field field : fields) {
                fieldName = field.getName();
                try {
                    field.setAccessible(true);
                    fieldValue = field.get(value);
                } catch (IllegalAccessException e) {
                    continue;
                }
                jedis.hset(hashId, fieldName, StringUtil.toString(fieldValue, StringUtils.EMPTY));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeJedis(jedis);
        }
    }

    @Override
    public <T> void setHash(CacheKeyGenerator<T> keyGenerator, Collection<T> values, Class<T> clazz) {
        Guard.notNull(values, "values");

        MetaClassReflector metaClassReflector = MetaClassReflector.forClass(clazz);
        Field[] fields = metaClassReflector.getFields();

        String fieldName;
        Object fieldValue;
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();
            for (Field field : fields) {
                fieldName = field.getName();
                for (T objItem : values) {
                    try {
                        field.setAccessible(true);
                        fieldValue = field.get(objItem);
                    } catch (IllegalAccessException e) {
                        continue;
                    }
                    pipeline.hset(keyGenerator.generate(objItem), fieldName, StringUtil.toString(fieldValue, StringUtils.EMPTY));
                }
            }
            pipeline.sync();
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeJedis(jedis);
        }
    }

    @Override
    public String getHash(String hashId, String field) {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            return jedis.hget(hashId, field);
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeJedis(jedis);
        }
    }

    @Override
    public <T> List<T> getHash(String patternHashId, String[] fields, CacheObjectGenerator<T> objectGenerator, Class<T> clazz) {
        Jedis jedis = null;
        List<T> list = new ArrayList<>();
        try {
            jedis = this.jedisPool.getResource();
            Map<String, String> valuePair;
            Set<String> hashIds = jedis.keys(patternHashId);
            if (hashIds != null && hashIds.size() > 0) {
                for (String hashId : hashIds) {
                    valuePair = new HashMap<>();
                    for (String field : fields) {
                        String value = jedis.hget(hashId, field);
                        valuePair.put(field, value);
                    }
                    list.add(objectGenerator.generate(valuePair));
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeJedis(jedis);
        }
        return list;
    }

    @Override
    public Map<String, String> getHash(String hashId, String[] fields) {
        Map<String, String> map = new HashMap<>();
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            for (String field : fields) {
                String value = jedis.hget(hashId, field);
                map.put(field, value);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeJedis(jedis);
        }
        return map;
    }

    private void closeJedis(Jedis jedis) {
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

    private String getCombinedKey(String cacheName, String key) {
        if (StringUtils.isEmpty(cacheName)) {
            return key;
        }
        return cacheName + ":" + key;
    }
}
