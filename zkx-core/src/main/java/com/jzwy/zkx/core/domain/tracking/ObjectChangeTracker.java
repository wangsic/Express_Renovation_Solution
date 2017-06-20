package com.jzwy.zkx.core.domain.tracking;

import com.jzwy.zkx.core.exception.InvalidOperationException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
public class ObjectChangeTracker {

    private ObjectState objectState = ObjectState.Added;

    private boolean changeTrackingEnabled;

    private HashMap<String, Object> originalValues = new HashMap<>();
    private HashMap<String, Object> newValues = new HashMap<>();

    private HashMap<String, ArrayList<Object>> objectsAddedToCollections = new HashMap<>();
    private HashMap<String, ArrayList<Object>> objectsModifiedToCollections = new HashMap<>();
    private HashMap<String, ArrayList<Object>> objectsRemovedFromCollections = new HashMap<>();

    public boolean getChangeTrackingEnabled(){
        return this.changeTrackingEnabled;
    }

    public void setChangeTrackingEnabled(boolean enabled){
        this.changeTrackingEnabled = enabled;
    }

    public ObjectState getObjectState(){
        return this.objectState;
    }

    public void setObjectState(ObjectState state){
        if (this.changeTrackingEnabled) {
            this.objectState = state;
        }
    }

    public HashMap<String, Object> getOriginalValues(){
        if (this.originalValues == null){
            this.originalValues = new HashMap<>();
        }
        return this.originalValues;
    }

    public HashMap<String, Object> getNewValues(){
        if (this.newValues == null){
            this.newValues = new HashMap<>();
        }
        return this.newValues;
    }

    public HashMap<String, ArrayList<Object>> getObjectsAddedToCollections(){
        if (this.objectsAddedToCollections == null){
            this.objectsAddedToCollections = new HashMap<>();
        }
        return this.objectsAddedToCollections;
    }

    public HashMap<String, ArrayList<Object>> getObjectsModifiedToCollections(){
        if (this.objectsModifiedToCollections == null){
            this.objectsModifiedToCollections = new HashMap<>();
        }
        return this.objectsModifiedToCollections;
    }

    public HashMap<String, ArrayList<Object>> getObjectsRemovedFromCollections(){
        if (this.objectsRemovedFromCollections == null){
            this.objectsRemovedFromCollections = new HashMap<>();
        }
        return this.objectsRemovedFromCollections;
    }


    /**
     * 重置objectchangetracker的不变状态并清除原有的值以及对集合属性的变化记录
     */
    public void acceptChanges() {
        this.originalValues.clear();
        this.newValues.clear();
        this.objectsAddedToCollections.clear();
        this.objectsModifiedToCollections.clear();
        this.objectsRemovedFromCollections.clear();
        this.changeTrackingEnabled = true;
        this.objectState = ObjectState.Unchanged;
    }

    /**
     *
     * @param propertyName
     * @param originalValue
     */
    public void recordOriginalValue(String propertyName, Object originalValue) {
        if (this.changeTrackingEnabled && objectState != ObjectState.Added) {
            this.getOriginalValues().put(propertyName, originalValue);
        }
    }

    /**
     *
     * @param propertyName
     * @param newValue
     */
    public void recordNewValue(String propertyName, Object newValue)
    {
        if (this.changeTrackingEnabled && objectState != ObjectState.Added) {
            this.getNewValues().put(propertyName, newValue);
        }
    }


    /**
     *
     * @param propertyName
     * @param addedItem
     */
    public void recordAdditionToCollectionProperties(String propertyName, Object addedItem){

        if (this.changeTrackingEnabled) {

            if (this.objectsRemovedFromCollections.containsKey(propertyName)) {
                ArrayList<Object> removedObjectList = this.objectsRemovedFromCollections.get(propertyName);
                if (removedObjectList.contains(addedItem)) {
                    removedObjectList.remove(addedItem);
                }
            }

            ArrayList<Object> addedObjectList;

            if (!this.objectsAddedToCollections.containsKey(propertyName)) {
                addedObjectList = new ArrayList<>();
                addedObjectList.add(addedItem);
                this.objectsAddedToCollections.put(propertyName, addedObjectList);
            } else {
                addedObjectList = this.objectsAddedToCollections.get(propertyName);
                if (addedObjectList == null) {
                    addedObjectList = new ArrayList<>();
                }
                addedObjectList.add(addedItem);
            }
        }
    }


    /**
     *
     * @param propertyName
     * @param modifiedItem
     */
    public void recordModificationToCollectionProperties(String propertyName, Object modifiedItem) {

        if (this.changeTrackingEnabled)
        {
            if (this.objectsAddedToCollections.containsKey(propertyName) &&
                this.objectsAddedToCollections.get(propertyName).contains(modifiedItem))
            {
                throw new InvalidOperationException("");
            }

            if (this.objectsRemovedFromCollections.containsKey(propertyName) &&
                this.objectsRemovedFromCollections.get(propertyName).contains(modifiedItem))
            {
                throw new InvalidOperationException("");
            }

            if (!this.objectsModifiedToCollections.containsKey(propertyName))
            {
                this.objectsModifiedToCollections.put(propertyName, new ArrayList<>());
                this.objectsModifiedToCollections.get(propertyName).add(modifiedItem);
            }
            else
            {
                this.objectsModifiedToCollections.get(propertyName).add(modifiedItem);
            }
        }

    }


    /**
     *
     * @param propertyName
     * @param deletedItem
     */
    public void recordRemovalFromCollectionProperties(String propertyName, Object deletedItem)
    {
        if (this.changeTrackingEnabled)
        {
            if (this.objectsAddedToCollections.containsKey(propertyName))
            {
                ArrayList<Object> addedObjectList = this.objectsAddedToCollections.get(propertyName);

                if (addedObjectList != null && addedObjectList.contains(deletedItem)) {

                    addedObjectList.remove(deletedItem);

                    if (addedObjectList.size() == 0 ) {
                        this.objectsAddedToCollections.remove(propertyName);
                    }
                    return;
                }
            }

            if (this.objectsModifiedToCollections.containsKey(propertyName))
            {
                ArrayList<Object> modifiedObjectList = this.objectsModifiedToCollections.get(propertyName);

                if (modifiedObjectList != null && modifiedObjectList.contains(deletedItem)) {

                    modifiedObjectList.remove(deletedItem);

                    if (modifiedObjectList.size() == 0 ) {
                        this.objectsModifiedToCollections.remove(propertyName);
                    }
                    return;
                }
            }

            if (!this.objectsRemovedFromCollections.containsKey(propertyName))
            {
                this.objectsRemovedFromCollections.put(propertyName, new ArrayList<>());
                this.objectsRemovedFromCollections.get(propertyName).add(deletedItem);
            }
            else
            {
                if (!this.objectsRemovedFromCollections.get(propertyName).contains(deletedItem))
                {
                    this.objectsRemovedFromCollections.get(propertyName).add(deletedItem);
                }
            }
        }
    }

}
