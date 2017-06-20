package com.jzwy.zkx.core.domain.tracking;

import com.jzwy.zkx.core.exception.InvalidOperationException;
import com.jzwy.zkx.core.support.list.CollectionChangeEmitter;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;

/**
 * ObjectChangeTrackerUtils
 */
public class ObjectChangeTrackerUtils {

    /**
     * 开启跟踪
     *
     * @param changeTrackable
     */
    public static void startTracking(ObjectChangeTrackable changeTrackable) {
        changeTrackable.getChangeTracker().setChangeTrackingEnabled(true);
        startCollectionPropertyTracking(changeTrackable);
    }

    /**
     * 标记为已更新
     *
     * @param changeTrackable
     */
    public static void markModified(ObjectChangeTrackable changeTrackable) {
        changeTrackable.getChangeTracker().setObjectState(ObjectState.Modified);
    }

    /**
     * 停止对象变化跟踪
     *
     * @param changeTrackable
     */
    public static void stopTracking(ObjectChangeTrackable changeTrackable) {
        changeTrackable.getChangeTracker().setChangeTrackingEnabled(false);
    }

    /**
     * 接受对象变化
     *
     * @param changeTrackable
     */
    public static void acceptChanges(ObjectChangeTrackable changeTrackable) {
        changeTrackable.getChangeTracker().acceptChanges();
        startCollectionPropertyTracking(changeTrackable);
    }

    /**
     * @param changeTrackable
     */
    public static void startTrackingAll(ObjectChangeTrackable changeTrackable) {
        ChangeTrackerIterator iterator = ChangeTrackerIterator.create(changeTrackable);
        iterator.execute(arg -> startTracking(arg));
    }

    /**
     * 停止跟踪实体对象和与实体对象关联的所有对象属性
     *
     * @param changeTrackable
     */
    public static void stopTrackingAll(ObjectChangeTrackable changeTrackable) {
        ChangeTrackerIterator iterator = ChangeTrackerIterator.create(changeTrackable);
        iterator.execute(arg -> stopTracking(arg));
    }


    /**
     * 接受跟该实体关联的所有实体的变化
     *
     * @param changeTrackable
     */
    public static void acceptAllChanges(ObjectChangeTrackable changeTrackable) {
        ChangeTrackerIterator iterator = ChangeTrackerIterator.create(changeTrackable);
        iterator.execute((ObjectChangeTrackable arg) -> acceptChanges(arg));
    }

    /**
     * 开启对象集合属性的变化跟踪
     *
     * @param changeTrackable
     */
    private static void startCollectionPropertyTracking(ObjectChangeTrackable changeTrackable) {

        if (changeTrackable.getChangeTracker().getChangeTrackingEnabled()) {
            /**
             throw new InvalidOperationException("当开启属性变化跟踪的时候不能设置集合属性");
             */
        }

        PropertyDescriptor[] propDescArray = BeanUtils.getPropertyDescriptors(changeTrackable.getClass());

        for (PropertyDescriptor propDescItem : propDescArray) {
            if (CollectionChangeEmitter.class.isAssignableFrom(propDescItem.getPropertyType())) {

                CollectionChangeEmitter changeEmitter;

                try {
                    changeEmitter = (CollectionChangeEmitter) propDescItem.getReadMethod().invoke(changeTrackable, new Object[]{});
                } catch (Exception e) {
                    throw new InvalidOperationException(e);
                }

                changeEmitter.addCollectionChangeListener(new EntityCollectionChangeListener(changeTrackable.getChangeTracker()));
            }
        }
    }
}
