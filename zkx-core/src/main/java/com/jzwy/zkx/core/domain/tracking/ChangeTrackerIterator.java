package com.jzwy.zkx.core.domain.tracking;

import com.jzwy.zkx.core.exception.InvalidOperationException;
import com.jzwy.zkx.core.lambda.func.ActionOfT;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 变化跟踪迭代执行器
 */
public final class ChangeTrackerIterator implements Iterable<ObjectChangeTrackable> {

    private ArrayList<ObjectChangeTrackable> objectChangeTrackerList = new ArrayList<>();

    public static <T extends ObjectChangeTrackable> ChangeTrackerIterator create(T entity) {
        ChangeTrackerIterator iterator = new ChangeTrackerIterator();
        iterator.visit(entity);
        return iterator;
    }

    /**
     * 为每个IObjectChangeTracker执行执行的操作
     *
     * @param action
     */
    public void execute(ActionOfT<ObjectChangeTrackable> action) {
        if (this.objectChangeTrackerList != null) {
            for (ObjectChangeTrackable changeTrackable : this.objectChangeTrackerList) {
                action.invoke(changeTrackable);
            }
        }
    }

    /**
     * 遍历对象中的导航属性 1：1 or 1:Many
     *
     * @param entity
     */
    private void visit(ObjectChangeTrackable entity) {
        if (entity != null && !this.objectChangeTrackerList.contains(entity)) {
            this.objectChangeTrackerList.add(entity);
            this.traverse(entity);
        }
    }

    private void traverse(ObjectChangeTrackable entity) {
        Class type = entity.getClass();

        PropertyDescriptor[] propDescArray = BeanUtils.getPropertyDescriptors(type);

        for (PropertyDescriptor propDesc : propDescArray) {

            if (ObjectChangeTrackable.class.isAssignableFrom(propDesc.getPropertyType())) {
                this.visit((ObjectChangeTrackable) propDesc.getValue(null));
            }

            if (Iterable.class.isAssignableFrom(propDesc.getPropertyType())) {

                Iterable<ObjectChangeTrackable> trackables;

                try {
                    trackables = (Iterable<ObjectChangeTrackable>) propDesc.getReadMethod().invoke(entity, new Object[]{});
                } catch (Exception e) {
                    throw new InvalidOperationException(e);
                }

                if (trackables != null) {
                    for (ObjectChangeTrackable trackable : trackables) {
                        this.visit(trackable);
                    }
                }
            }
        }
    }


    @Override
    public Iterator<ObjectChangeTrackable> iterator() {
        return this.objectChangeTrackerList.iterator();
    }
}