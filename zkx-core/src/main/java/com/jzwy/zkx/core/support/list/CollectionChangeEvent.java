package com.jzwy.zkx.core.support.list;


import java.util.EventObject;
import java.util.List;

/**
 * 集合变化事件数据
 */
public class CollectionChangeEvent extends EventObject {

    private CollectionChangedAction action;
    private List newItems;
    private List oldItems;

    public CollectionChangeEvent(Object source, CollectionChangedAction action, List newItems, List oldItems) {
        super(source);
        this.action = action;
        this.newItems = newItems;
        this.oldItems = oldItems;
    }

    public CollectionChangedAction getAction() {
        return this.action;
    }

    public List getNewItems() {
        return this.newItems;
    }

    public List getOldItems() {
        return this.oldItems;
    }

}
