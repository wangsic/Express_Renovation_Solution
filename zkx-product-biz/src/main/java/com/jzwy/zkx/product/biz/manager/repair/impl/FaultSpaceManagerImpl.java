package com.jzwy.zkx.product.biz.manager.repair.impl;

import com.jzwy.zkx.common.cache.CacheProvider;
import com.jzwy.zkx.core.dao.Dao;
import com.jzwy.zkx.core.manager.BaseManager;
import com.jzwy.zkx.product.biz.domain.repair.FaultSpaceDO;
import com.jzwy.zkx.product.biz.manager.repair.FaultSpaceManager;
import com.jzwy.zkx.product.service.repair.query.FaultSpaceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * FaultSpaceManagerImpl
 */
public class FaultSpaceManagerImpl extends BaseManager<FaultSpaceDO, FaultSpaceQuery> implements FaultSpaceManager {

    private static final String CACHE_NAME_FAULT_SPACE_ID = "FaultSpaceId";

    @Qualifier("localCacheProvider")
    @Autowired
    private CacheProvider cacheProvider;

    public FaultSpaceManagerImpl(Dao<FaultSpaceDO, FaultSpaceQuery> dao) {
        super(dao);
    }

    @Override
    public Integer update(FaultSpaceDO item) throws Exception {
        String cacheKey = "id:" + item.getId();
        this.cacheProvider.remove(CACHE_NAME_FAULT_SPACE_ID, cacheKey);
        return super.update(item);
    }

    @Override
    public FaultSpaceDO queryById(Long id) throws Exception {
        String cacheKey = "id:" + id;
        FaultSpaceDO faultSpaceDO = this.cacheProvider.get(CACHE_NAME_FAULT_SPACE_ID, cacheKey, FaultSpaceDO.class);
        if (faultSpaceDO == null) {
            faultSpaceDO = super.queryById(id);
            this.cacheProvider.set(CACHE_NAME_FAULT_SPACE_ID, cacheKey, faultSpaceDO);
        }
        return faultSpaceDO;
    }
}
