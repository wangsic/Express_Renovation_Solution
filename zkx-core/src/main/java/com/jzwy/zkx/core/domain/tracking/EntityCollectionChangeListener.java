package com.jzwy.zkx.core.domain.tracking;


import com.jzwy.zkx.core.support.list.CollectionChangeEvent;
import com.jzwy.zkx.core.support.list.CollectionChangeListener;
import com.jzwy.zkx.core.support.list.CollectionChangedAction;

/**
 *
 */
public class EntityCollectionChangeListener implements CollectionChangeListener {

    private ObjectChangeTracker tracker;

    public EntityCollectionChangeListener(ObjectChangeTracker tracker){
        this.tracker = tracker;
    }

    @Override
    public void collectionChanged(CollectionChangeEvent event) {

        if (event.getNewItems() != null && event.getNewItems().size() > 0) {

            String propertyTypeName = event.getNewItems().get(0).getClass().getSimpleName();

            for (Object item : event.getNewItems())
            {
                if (item instanceof ObjectChangeTrackable) {

                    ObjectChangeTrackable trackableItem = (ObjectChangeTrackable)item;

                    if (tracker.getChangeTrackingEnabled()) {
                        if (!trackableItem.getChangeTracker().getChangeTrackingEnabled()) {
                            ObjectChangeTrackerUtils.startTracking(trackableItem);
                        }
                        if (event.getAction() == CollectionChangedAction.Add) {
                            this.tracker.recordAdditionToCollectionProperties(propertyTypeName, item);
                        } else if (event.getAction() == CollectionChangedAction.Update) {
                            this.tracker.recordModificationToCollectionProperties(propertyTypeName, item);
                        }
                    }
                }
            }
        }

        if (event.getOldItems() != null && event.getOldItems().size() > 0) {
            String propertyTypeName = event.getNewItems().get(0).getClass().getSimpleName();

            if (event.getAction() == CollectionChangedAction.Remove) {
                for (Object item : event.getOldItems()) {
                    if (tracker.getChangeTrackingEnabled()) {
                        this.tracker.recordRemovalFromCollectionProperties(propertyTypeName, item);
                    }
                }
            }
        }
    }
}
