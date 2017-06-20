package com.jzwy.zkx.core.service.contract;

/**
 * 分页信息
 */
public class Pagination {

    private int pageIndex;
    private int pageSize;
    private int totalPages;
    private long totalRecords;

    /**
     * 获取当前页号
     * @return
     */
    public int getPageIndex() {
        return pageIndex;
    }

    /**
     * 设置当前页号
     * @param pageIndex
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * 获取每页大小
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页大小
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取总页数
     * @return
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * 设置总页数
     * @param totalPages
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * 获取总记录数
     * @return
     */
    public long getTotalRecords() {
        return totalRecords;
    }

    /**
     * 设置总记录数
     * @param totalRecords
     */
    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }
}
