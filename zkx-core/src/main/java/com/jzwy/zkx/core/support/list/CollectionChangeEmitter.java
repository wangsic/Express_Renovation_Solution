package com.jzwy.zkx.core.support.list;

/**
 * 集合变化事件添加移除器
 */
public interface CollectionChangeEmitter {

    /**
     * 添加集合变化监听器
     *
     * @param listener
     */
    void addCollectionChangeListener(CollectionChangeListener listener);

    /**
     * 移除集合变化监听器
     *
     * @param listener
     */
    void removeCollectionChangeListener(CollectionChangeListener listener);
}
