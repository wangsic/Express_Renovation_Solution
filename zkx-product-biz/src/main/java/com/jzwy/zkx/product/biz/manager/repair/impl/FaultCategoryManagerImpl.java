package com.jzwy.zkx.product.biz.manager.repair.impl;

import com.jzwy.zkx.common.cache.CacheProvider;
import com.jzwy.zkx.core.dao.Dao;
import com.jzwy.zkx.core.manager.BaseManager;
import com.jzwy.zkx.product.biz.domain.repair.FaultCategoryDO;
import com.jzwy.zkx.product.biz.manager.repair.FaultCategoryManager;
import com.jzwy.zkx.product.service.repair.query.FaultCategoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * FaultCategoryManagerImpl
 */
public class FaultCategoryManagerImpl extends BaseManager<FaultCategoryDO,FaultCategoryQuery> implements FaultCategoryManager{

    private static final String CACHE_NAME_FAULT_CATEGORY_ID = "FaultCategoryId";

    @Qualifier("localCacheProvider")
    @Autowired
    private CacheProvider cacheProvider;

    public FaultCategoryManagerImpl(Dao<FaultCategoryDO, FaultCategoryQuery> dao) {
        super(dao);
    }

    @Override
    public Integer update(FaultCategoryDO item) throws Exception {
        String cacheKey = "id:" + item.getId();
        this.cacheProvider.remove(CACHE_NAME_FAULT_CATEGORY_ID,cacheKey);
        return super.update(item);
    }

    @Override
    public FaultCategoryDO queryById(Long id) throws Exception {
        String cacheKey = "id:" + id;
        FaultCategoryDO faultCategoryDO = this.cacheProvider.get(CACHE_NAME_FAULT_CATEGORY_ID,cacheKey,FaultCategoryDO.class);
        if (faultCategoryDO == null) {
            faultCategoryDO = super.queryById(id);
            this.cacheProvider.set(CACHE_NAME_FAULT_CATEGORY_ID,cacheKey,faultCategoryDO);
        }
        return faultCategoryDO;
    }
}
