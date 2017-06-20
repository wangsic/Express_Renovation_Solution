package com.jzwy.zkx.core.support.list;

import java.util.EventListener;

/**
 * 集合变化所触发事件侦听器
 */
public interface CollectionChangeListener extends EventListener {
    void collectionChanged(CollectionChangeEvent event);
}
