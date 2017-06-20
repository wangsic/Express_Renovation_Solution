package com.jzwy.zkx.core.support.list;


import java.util.*;

/**
 * 可跟踪的ArrayList
 */
public class TrackableArrayList<E> extends Observable implements List<E>, Iterable<E>, CollectionChangeEmitter {

    private List<E> internalList = new ArrayList<>();
    private List<CollectionChangeListener> changeListeners = new ArrayList<>();

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.internalList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean added = this.internalList.addAll(c);
        if (added) {
            this.onCollectionChanged(CollectionChangedAction.Add, (E[]) c.toArray());
        }
        return added;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean added = this.internalList.addAll(index, c);
        if (added) {
            this.onCollectionChanged(CollectionChangedAction.Add, (E[]) c.toArray());
        }
        return added;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removed = this.internalList.removeAll(c);
        if (removed) {
            this.onCollectionChanged(CollectionChangedAction.Remove, (E[]) c.toArray());
        }
        return removed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.internalList.retainAll(c);
    }

    @Override
    public void clear() {
        this.internalList.clear();
    }

    @Override
    public E get(int index) {
        return this.internalList.get(index);
    }

    @Override
    public E set(int index, E element) {
        this.internalList.set(index, element);
        this.onCollectionChanged(CollectionChangedAction.Replace, element);
        return element;
    }

    @Override
    public void add(int index, E element) {
        this.internalList.add(index, element);
        this.onCollectionChanged(CollectionChangedAction.Add, element);
    }

    @Override
    public E remove(int index) {
        return this.internalList.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return this.internalList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.internalList.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return this.internalList.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return this.internalList.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return this.internalList.subList(fromIndex, toIndex);
    }

    @Override
    public void addCollectionChangeListener(CollectionChangeListener listener) {
        this.changeListeners.add(listener);
    }

    @Override
    public void removeCollectionChangeListener(CollectionChangeListener listener) {
        this.changeListeners.remove(listener);
    }

    public void fireCollectionItemUpdateChange(E updatedItem) {
        this.onCollectionChanged(CollectionChangedAction.Update, updatedItem);
    }

    @Override
    public int size() {
        return this.internalList.size();
    }

    @Override
    public boolean isEmpty() {
        return this.internalList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.internalList.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return this.internalList.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.internalList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.internalList.toArray(a);
    }

    @Override
    public boolean add(E e) {
        boolean added = this.internalList.add(e);
        if (added) {
            this.onCollectionChanged(CollectionChangedAction.Add, e);
        }
        return added;
    }

    @Override
    public boolean remove(Object o) {
        boolean removed = this.internalList.remove(o);
        if (removed) {
            this.onCollectionChanged(CollectionChangedAction.Remove, (E) o);
        }
        return removed;
    }

    protected void onCollectionChanged(CollectionChangedAction changedAction, E... items) {
        if (this.changeListeners != null) {
            List<E> actionItems = new ArrayList<>();
            actionItems.addAll(Arrays.asList(items));

            for (CollectionChangeListener changeListener : this.changeListeners) {
                CollectionChangeEvent changeEvent = new CollectionChangeEvent(
                        this,
                        changedAction,
                        changedAction == CollectionChangedAction.Remove ? null : actionItems,
                        changedAction == CollectionChangedAction.Add ? null : actionItems);
                changeListener.collectionChanged(changeEvent);
            }
        }
    }

}