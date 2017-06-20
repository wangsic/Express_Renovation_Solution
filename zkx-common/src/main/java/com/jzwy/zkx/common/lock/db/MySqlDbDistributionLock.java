package com.jzwy.zkx.common.lock.db;

import com.jzwy.zkx.common.lock.DistributionLock;
import com.jzwy.zkx.common.util.DateUtils;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * MySqlDbDistributionLock
 */
public class MySqlDbDistributionLock implements DistributionLock {

    private static Lock syncLock = new ReentrantLock();

    private boolean existingConcurrencyTable;
    private String lockTableName;
    private JdbcTemplate jdbcTemplate;
    private TransactionTemplate transactionTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public void setLockTableName(String lockTableName) {
        this.lockTableName = lockTableName;
    }

    @Override
    public void lock(String lockName, int timeout, TimeUnit timeUnit) {
        throw new NotImplementedException();
    }

    @Override
    public Boolean tryLock(String lockName, int lockExpireSeconds) {
        throw new NotImplementedException();
    }

    @Override
    public Boolean tryLock(String lockName, int timeout, TimeUnit timeUnit, int lockExpireSeconds) {
        if (StringUtils.isEmpty(lockName)) {
            throw new RuntimeException("分布式锁名称为空");
        }

        if (!existingConcurrencyTable) {
            syncLock.lock();
            if (!existingConcurrencyTable) {
                try {
                    Integer count = this.jdbcTemplate.query("SELECT count(1) as count FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?",
                            new Object[]{lockTableName},
                            (resultSet) -> {
                                return resultSet.getInt("count");
                            });
                    existingConcurrencyTable = count.equals(1);
                } catch (Exception e) {
                    throw e;
                } finally {
                    syncLock.unlock();
                }
                if (!existingConcurrencyTable) {
                    throw new RuntimeException("业务操作并发控制表不存在, 请先创建业务操作并发控制表");
                }
            }
        }

        try {
            Date currentTime = new Date();
            Calendar calendar = Calendar.getInstance();
            int field = Calendar.SECOND;
            if (TimeUnit.MINUTES == timeUnit) {
                field = Calendar.MINUTE;
            } else if (TimeUnit.HOURS == timeUnit) {
                field = Calendar.HOUR;
            } else if (TimeUnit.DAYS == timeUnit) {
                field = Calendar.DATE;
            }
            calendar.setTime(currentTime);
            calendar.add(field, timeout);

            BizConcurrencyControl bizConcurrencyControl = new BizConcurrencyControl();
            bizConcurrencyControl.setLockName(lockName);
            bizConcurrencyControl.setCreateTime(currentTime);
            bizConcurrencyControl.setExpirationTime(calendar.getTime());

            Integer insertedSuccess = this.jdbcTemplate.update(
                    "insert into " + lockTableName + " (" +
                            "lock_name" +
                            ",create_time" +
                            ",expiration_time" +
                            ",creator_name" +
                            ") values (?, ?, ?, ?) ",
                    new Object[]{bizConcurrencyControl.getLockName(),
                            bizConcurrencyControl.getCreateTime(),
                            bizConcurrencyControl.getExpirationTime(),
                            bizConcurrencyControl.getCreatorName()});

            return true;
        } catch (PersistenceException e) {
            if (e.getCause() instanceof MySQLIntegrityConstraintViolationException) {
                return false;
            } else {
                throw e;
            }
        } catch (DuplicateKeyException e) {
            if (e.getCause() instanceof MySQLIntegrityConstraintViolationException) {
                return false;
            } else {
                throw e;
            }
        }
    }

    @Override
    public Boolean tryLock(Collection<String> lockNames, int timeout, TimeUnit timeUnit) {
        if (lockNames == null || lockNames.isEmpty()) {
            throw new RuntimeException("分布式锁名称为空");
        }

        Date currentTime = new Date();
        Calendar calendar = Calendar.getInstance();
        int field = Calendar.SECOND;
        if (TimeUnit.MINUTES == timeUnit) {
            field = Calendar.MINUTE;
        } else if (TimeUnit.HOURS == timeUnit) {
            field = Calendar.HOUR;
        } else if (TimeUnit.DAYS == timeUnit) {
            field = Calendar.DATE;
        }
        calendar.setTime(currentTime);
        calendar.add(field, timeout);

        return this.transactionTemplate.execute(transactionStatus -> {
            try {
                List<BizConcurrencyControl> bizConcurrencyControls = new ArrayList<>();
                for (String lockName : lockNames) {
                    BizConcurrencyControl bizConcurrencyControl = new BizConcurrencyControl();
                    bizConcurrencyControl.setLockName(lockName);
                    bizConcurrencyControl.setCreateTime(currentTime);
                    bizConcurrencyControl.setExpirationTime(calendar.getTime());
                    bizConcurrencyControls.add(bizConcurrencyControl);
                }

                int[] insertedSuccess = this.jdbcTemplate.batchUpdate(
                        "insert into " + lockTableName + " (" +
                                "lock_name" +
                                ",create_time" +
                                ",expiration_time" +
                                ",creator_name" +
                                ") values (?, ?, ?, ?) ", new BatchPreparedStatementSetter() {
                            @Override
                            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                                preparedStatement.setString(0, bizConcurrencyControls.get(i).getLockName());
                                preparedStatement.setDate(1, java.sql.Date.valueOf(DateUtils.toLocalDate(bizConcurrencyControls.get(i).getCreateTime())));
                                preparedStatement.setDate(2, java.sql.Date.valueOf(DateUtils.toLocalDate(bizConcurrencyControls.get(i).getExpirationTime())));
                                preparedStatement.setString(3, bizConcurrencyControls.get(i).getCreatorName());
                            }

                            @Override
                            public int getBatchSize() {
                                return bizConcurrencyControls.size();
                            }
                        });
                return true;
            } catch (PersistenceException e) {
                transactionStatus.setRollbackOnly();
                if (e.getCause() instanceof MySQLIntegrityConstraintViolationException) {
                    return false;
                } else {
                    throw e;
                }
            } catch (DuplicateKeyException e) {
                transactionStatus.setRollbackOnly();
                if (e.getCause() instanceof MySQLIntegrityConstraintViolationException) {
                    return false;
                } else {
                    throw e;
                }
            }
        });
    }

    @Override
    public void unlock(String lockName) {
        jdbcTemplate.update("delete from " + lockTableName + " where lock_name = ? ", new Object[]{lockName});
    }

    @Override
    public void unlock(Collection<String> lockNames) {
        if (CollectionUtils.isEmpty(lockNames)) {
            return;
        }
        for (String lockName : lockNames) {
            jdbcTemplate.update("delete from " + lockTableName + " where lock_name = ? ", new Object[]{lockName});
        }
    }

}
