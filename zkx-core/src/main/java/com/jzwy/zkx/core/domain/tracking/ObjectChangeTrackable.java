package com.jzwy.zkx.core.domain.tracking;

import java.beans.PropertyChangeListener;

/**
 * 代表该对象的状态属性变化可被跟踪
 */
public interface ObjectChangeTrackable extends PropertyChangeListener {

    ObjectChangeTracker getChangeTracker();
}
