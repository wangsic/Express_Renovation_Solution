package com.jzwy.zkx.product.rest.api.common;

import com.alibaba.fastjson.JSONObject;
import com.jzwy.zkx.common.util.CollectionUtils;
import com.jzwy.zkx.common.util.DateUtils;
import com.jzwy.zkx.common.util.ValueUtils;
import com.jzwy.zkx.core.service.contract.PagedResultsResponse;
import com.jzwy.zkx.core.service.contract.Pagination;
import com.jzwy.zkx.core.service.contract.Response;
import com.jzwy.zkx.core.service.contract.ResponseCode;
import com.jzwy.zkx.product.rest.api.common.http.RequestJsonWrapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author user
 */
public abstract class BaseAction {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * ---- response ----
     */
    public Integer getInt(HttpServletRequest request, String name, Integer defaultValue) throws Exception {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        String valueStr = request.getParameter(name); // 要查询的对象
        if (null == valueStr) {
            return defaultValue;
        }
        Integer value;
        try {
            value = Integer.parseInt(valueStr);
        } catch (Exception e) {
            throw new Exception("Parse parmeter failure, name=" + name + ",value=" + valueStr, e);
        }
        return value;
    }

    public String getString(HttpServletRequest request, String name, String defaultValue) throws Exception {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        String value = request.getParameter(name); // 要查询的对象
        if (null == value) {
            return defaultValue;
        }
        return value.trim();
    }

    public Long getLong(HttpServletRequest request, String name, Long defaultValue) throws Exception {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        String valueStr = request.getParameter(name); // 要查询的对象
        if (null == valueStr) {
            return defaultValue;
        }
        Long value;
        try {
            value = Long.parseLong(valueStr);
        } catch (Exception e) {
            throw new Exception("Parse parmeter failure, name=" + name + ",value=" + valueStr, e);
        }
        return value;
    }

    public Double getDouble(HttpServletRequest request, String name, Double defaultValue) throws Exception {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        String valueStr = request.getParameter(name); // 要查询的对象
        if (null == valueStr) {
            return defaultValue;
        }
        Double value;
        try {
            value = Double.parseDouble(valueStr);
        } catch (Exception e) {
            throw new Exception("Parse parmeter failure, name=" + name + ",value=" + valueStr, e);
        }
        return value;
    }

    public Float getFloat(HttpServletRequest request, String name, Float defaultValue) throws Exception {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        String valueStr = request.getParameter(name); // 要查询的对象
        if (null == valueStr) {
            return defaultValue;
        }
        Float value;
        try {
            value = Float.parseFloat(valueStr);
        } catch (Exception e) {
            throw new Exception("Parse parmeter failure, name=" + name + ",value=" + valueStr, e);
        }
        return value;
    }

    public Date getDate(HttpServletRequest request, String name, Date defaultValue) throws Exception {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        String valueStr = request.getParameter(name); // 要查询的对象
        if (null == valueStr) {
            return defaultValue;
        }
        Date value;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.Format_1);
            value = simpleDateFormat.parse(valueStr);
        } catch (Exception e) {
            throw new Exception("Parse parmeter failure, name=" + name + ",value=" + valueStr, e);
        }
        return value;
    }

    protected JSONObject getPostJSONData(HttpServletRequest request) throws Exception {
        return RequestJsonWrapper.getPostJSONData(request);
    }

    protected String getStringFromCookie(HttpServletRequest request, String name, String defaultValue)
            throws Exception {
        Cookie[] cookies = request.getCookies();
        if (CollectionUtils.isEmpty(cookies) || StringUtils.isEmpty(name)) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    protected void processXLSMHead(HttpServletResponse response, String fileName) {
        response.reset();
        int buffer = 4096;
        response.setContentType("application/xls;charset = utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setBufferSize(buffer);
    }

    protected <T> Response<T> newDataReturnJson(T data) {
        Response<T> response = new Response<>();
        response.setCode(ResponseCode.CODE_SUCCESS);
        response.setMessage(ResponseCode.MSG_SUCCESS);
        response.setData(data);
        return response;
    }

    protected <T> Response newListReturnResponse(List<T> data, Pagination pagination) {
        PagedResultsResponse<T> pagedResultsResponse = new PagedResultsResponse<>();
        pagedResultsResponse.setCode(ResponseCode.CODE_SUCCESS);
        pagedResultsResponse.setMessage(ResponseCode.MSG_SUCCESS);
        pagedResultsResponse.setPage(pagination);
        pagedResultsResponse.setData(data);
        return pagedResultsResponse;
    }

    protected Response newErrorReturnJson(String message) {
        return newErrorReturnJson(null, message);
    }

    protected Response newErrorReturnJson(String code, String message) {
        Response<Void> response = new Response<>();
        response.setCode(ValueUtils.getNotNull(code, ResponseCode.DEFAULT_CODE_FAILURE));
        response.setMessage(ValueUtils.getNotNull(message, ResponseCode.DEFAULT_MSG_FAILURE));
        return response;
    }
}
