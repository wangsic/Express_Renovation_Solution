package com.jzwy.zkx.core.domain;

import com.jzwy.zkx.core.domain.tracking.EntityCollectionChangeListener;
import com.jzwy.zkx.core.domain.tracking.ObjectChangeTrackable;
import com.jzwy.zkx.core.domain.tracking.ObjectChangeTracker;
import com.jzwy.zkx.core.domain.tracking.ObjectState;
import com.jzwy.zkx.core.exception.InvalidOperationException;
import com.jzwy.zkx.core.lambda.func.Action;
import com.jzwy.zkx.core.support.list.CollectionChangeEmitter;
import com.jzwy.zkx.core.support.list.CollectionChangeListener;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.Objects;

/**
 * 代表可跟踪状态属性变化的抽象实体
 */
public abstract class AbstractTrackableEntity<I extends Serializable> extends AbstractAuditableEntity<I> implements ObjectChangeTrackable {

    private ObjectChangeTracker changeTracker = new ObjectChangeTracker();

    private CollectionChangeListener listChangeListener;

    @Override
    public ObjectChangeTracker getChangeTracker() {
        if (this.changeTracker == null) {
            this.changeTracker = new ObjectChangeTracker();
        }
        return this.changeTracker;
    }

    protected CollectionChangeListener getListChangeListener() {
        if (this.listChangeListener == null) {
            this.listChangeListener = new EntityCollectionChangeListener(this.changeTracker);
        }
        return this.listChangeListener;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (this.changeTracker.getObjectState() != ObjectState.Added &&
                this.changeTracker.getObjectState() != ObjectState.Deleted) {
            this.changeTracker.setObjectState(ObjectState.Modified);
        }
    }

    protected void firePropertyChanged(String propertyName,
                                       Object oldPropertyValue,
                                       Object newPropertyValue) {
        this.firePropertyChanged(propertyName, oldPropertyValue, newPropertyValue, null);
    }

    protected void firePropertyChanged(String propertyName,
                                       Object oldPropertyValue,
                                       Object newPropertyValue,
                                       Action assignNewValueAction) {
        this.firePropertyChanged(propertyName, oldPropertyValue, newPropertyValue, assignNewValueAction, false);
    }

    protected void firePropertyChanged(String propertyName,
                                       Object oldPropertyValue,
                                       Object newPropertyValue,
                                       Action assignNewValueAction,
                                       boolean onlyAddedState) {
        if (!Objects.equals(oldPropertyValue, newPropertyValue)) {

            if (onlyAddedState) {
                throw new InvalidOperationException(String.format("", propertyName));
            } else {
                if (assignNewValueAction != null) {
                    assignNewValueAction.invoke();
                }
                this.changeTracker.recordOriginalValue(propertyName, oldPropertyValue);
                this.changeTracker.recordNewValue(propertyName, newPropertyValue);
                this.propertyChange(new PropertyChangeEvent(this, propertyName, oldPropertyValue, newPropertyValue));
            }
        }
    }

    public void startCollectionPropertyTracking() {
        if (this.changeTracker.getChangeTrackingEnabled()) {
            throw new InvalidOperationException("当开启属性变化跟踪的时候不能设置集合属性");
        }

        PropertyDescriptor[] propDescArray = BeanUtils.getPropertyDescriptors(this.getClass());

        for (PropertyDescriptor propDescItem : propDescArray) {
            if (CollectionChangeEmitter.class.isAssignableFrom(propDescItem.getPropertyType())) {
                CollectionChangeEmitter changeEmitter = null;
                try {
                    changeEmitter = (CollectionChangeEmitter) propDescItem.getReadMethod().invoke(this, new Object[]{});
                } catch (Exception e) {
                    throw new InvalidOperationException(e);
                }
                changeEmitter.addCollectionChangeListener(this.getListChangeListener());
            }
        }
    }

}
