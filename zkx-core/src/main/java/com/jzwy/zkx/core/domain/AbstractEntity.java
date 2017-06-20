package com.jzwy.zkx.core.domain;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Objects;

/**
 * 代表抽象实体
 */
public abstract class AbstractEntity<I extends Serializable> implements Entity<I> {

    private static final int NONZERO_ODD_NUMBER = 274699673;
    private static final int PRIME_NUMBER = 3512835;

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (!(this.getClass().isAssignableFrom(obj.getClass()))) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        AbstractEntity<?> that = (AbstractEntity<?>) obj;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(NONZERO_ODD_NUMBER, PRIME_NUMBER);
        hashCodeBuilder.append(this.getId());
        return hashCodeBuilder.toHashCode();
    }
}
