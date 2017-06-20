package com.jzwy.zkx.core.service.contract;

import com.jzwy.zkx.core.support.query.PagedResult;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * 带可分页数据的响应结果
 */
public class PagedResultsResponse<T> extends Response<List<T>> {

    private Pagination page;

    /**
     * 获取分页信息
     *
     * @return
     */
    public Pagination getPage() {
        return page;
    }

    public void setPage(Pagination page) {
        this.page = page;
    }

    public static <T> PagedResultsResponse<T> writeSuccess(PagedResult<T> pagedResult) {
        PagedResultsResponse<T> pagedResultsResponse = new PagedResultsResponse<>();
        pagedResultsResponse.setCode(ResponseCode.CODE_SUCCESS);
        pagedResultsResponse.setMessage(ResponseCode.MSG_SUCCESS);
        if (pagedResult != null && CollectionUtils.isNotEmpty(pagedResult.getData())) {
            Pagination pagination = new Pagination();
            pagination.setPageIndex(pagedResult.getPageIndex());
            pagination.setPageSize(pagedResult.getPageSize());
            pagination.setTotalPages(pagedResult.getPageSize());
            pagination.setTotalRecords(pagedResult.getTotalRecords());
            pagedResultsResponse.setPage(pagination);
            pagedResultsResponse.setData(pagedResult.getData());
        }
        return pagedResultsResponse;
    }

    public static <T> PagedResultsResponse<T> writeSuccess(List<T> data, Pagination page) {
        PagedResultsResponse<T> pagedResultsResponse = new PagedResultsResponse<>();
        pagedResultsResponse.setCode(ResponseCode.CODE_SUCCESS);
        pagedResultsResponse.setMessage(ResponseCode.MSG_SUCCESS);
        pagedResultsResponse.setPage(page);
        pagedResultsResponse.setData(data);
        return pagedResultsResponse;
    }
}
