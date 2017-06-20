package com.jzwy.zkx.core.generator.snowflake;

import com.jzwy.zkx.core.generator.IdentityGenerator;
import com.jzwy.zkx.core.generator.SequenceGenerator;

/**
 * Snowflake
 */
public class SnowflakeIdGenerator implements IdentityGenerator, SequenceGenerator {

    @Override
    public Object next() {
        return this.generate();
    }

    @Override
    public Object generate() {
        IdWorker idWorker = new IdWorker(1, 1);
        return idWorker.nextId();
    }

    public static class IdWorker {

        private final long twepoch = 1288834974657L;
        private final long workerIdBits = 5L;
        private final long dataCenterIdBits = 5L;
        private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
        private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);
        private final long sequenceBits = 12L;
        private final long workerIdShift = sequenceBits;
        private final long dataCenterIdShift = sequenceBits + workerIdBits;
        private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;
        private final long sequenceMask = -1L ^ (-1L << sequenceBits);

        private long workerId;
        private long dataCenterId;
        private long sequence = 0L;
        private long lastTimestamp = -1L;

        public IdWorker(long workerId, long dataCenterId) {
            if (workerId > maxWorkerId || workerId < 0) {
                throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
            }
            if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
                throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDataCenterId));
            }
            this.workerId = workerId;
            this.dataCenterId = dataCenterId;
        }

        public synchronized long nextId() {
            long timestamp = timeGen();
            if (timestamp < lastTimestamp) {
                throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
            }
            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) & sequenceMask;
                if (sequence == 0) {
                    timestamp = tilNextMillis(lastTimestamp);
                }
            } else {
                sequence = 0L;
            }

            lastTimestamp = timestamp;

            return ((timestamp - twepoch) << timestampLeftShift) | (dataCenterId << dataCenterIdShift) | (workerId << workerIdShift) | sequence;
        }

        private long tilNextMillis(long lastTimestamp) {
            long timestamp = timeGen();
            while (timestamp <= lastTimestamp) {
                timestamp = timeGen();
            }
            return timestamp;
        }

        private long timeGen() {
            return System.currentTimeMillis();
        }
    }

}
