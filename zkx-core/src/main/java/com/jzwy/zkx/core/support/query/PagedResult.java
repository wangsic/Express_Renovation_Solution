package com.jzwy.zkx.core.support.query;

import com.jzwy.zkx.core.service.contract.Pagination;

import java.util.*;

/**
 * 分页结果接口
 */
public class PagedResult<T> implements List<T> {

    private Integer pageIndex;
    private Integer pageSize;
    private Integer totalPages;
    private Integer totalRecords;
    private List<T> data;

    public PagedResult(
            Integer pageIndex,
            Integer pageSize,
            Integer totalRecords,
            List<T> data) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalRecords = totalRecords;
        this.data = data;
        this.totalPages = this.calculateTotalPages(this.totalRecords, this.pageSize);
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public int getTotalRecords() {
        return this.totalRecords;
    }

    public List<T> getData() {
        return this.data;
    }

    public Pagination toPagination() {
        Pagination pagination = new Pagination();
        pagination.setPageIndex(this.pageIndex);
        pagination.setPageSize(this.pageSize);
        pagination.setTotalPages(this.totalPages);
        pagination.setTotalRecords(this.totalRecords);
        return pagination;
    }

    @Override
    public int size() {
        return this.data.size();
    }

    @Override
    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.data.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return this.data.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.data.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return this.data.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return this.data.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return this.data.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.data.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return this.data.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return this.data.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.data.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.data.retainAll(c);
    }

    @Override
    public void clear() {
        this.data.clear();
    }

    @Override
    public T get(int index) {
        return this.data.get(index);
    }

    @Override
    public T set(int index, T element) {
        return this.data.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        this.data.add(index, element);
    }

    @Override
    public T remove(int index) {
        return this.data.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return this.data.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.data.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return this.data.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return this.data.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return this.data.subList(fromIndex, toIndex);
    }

    private int calculateTotalPages(Integer totalRecords, Integer pageSize) {
        if (totalRecords == null || totalRecords == 0 || pageSize == null || pageSize == 0) return 0;

        if (Objects.equals(totalRecords % pageSize, 0)) {
            return (totalRecords / pageSize);
        } else {
            return (totalRecords / pageSize) + 1;
        }
    }
}