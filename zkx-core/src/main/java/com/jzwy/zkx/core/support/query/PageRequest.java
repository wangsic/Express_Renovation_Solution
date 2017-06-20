package com.jzwy.zkx.core.support.query;

/**
 * 分页请求
 */
public class PageRequest implements Pageable {

    private static final int DEFAULT_PAGE_INDEX = 1;
    private static final int DEFAULT_PAGE_SIZE = 50;

    private Integer pageIndex;// 查询第几页，注意：页数是从第1页开始的
    private Integer pageSize;// 每页记录数

    @Override
    public int getPageIndex() {
        return (0 >= pageIndex) ? DEFAULT_PAGE_INDEX : pageIndex;
    }

    @Override
    public int getPageSize() {
        return (0 > pageSize) ? 0 : pageSize;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = (pageIndex == null || 0 >= pageIndex) ? DEFAULT_PAGE_INDEX : pageIndex;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = (pageSize == null || 0 >= pageSize) ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public int getStartIndex() {
        int startIndex = (pageIndex - 1) * pageSize;
        return (startIndex < 0) ? 0 : startIndex;
    }
}
