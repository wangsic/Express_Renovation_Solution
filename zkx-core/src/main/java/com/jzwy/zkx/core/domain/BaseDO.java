package com.jzwy.zkx.core.domain;

/**
 * 抽象的DomainObject
 */
public abstract class BaseDO extends AbstractAuditableEntity<Long> {

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }
}
