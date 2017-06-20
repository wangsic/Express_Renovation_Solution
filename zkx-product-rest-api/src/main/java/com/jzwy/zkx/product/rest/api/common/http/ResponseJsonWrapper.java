package com.jzwy.zkx.product.rest.api.common.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jzwy.zkx.common.util.ValueUtils;
import com.jzwy.zkx.core.service.contract.ResponseCode;
import org.apache.http.util.EncodingUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;

/**
 *
 */
public class ResponseJsonWrapper {

    private static Logger logger = LogManager.getLogger(ResponseJsonWrapper.class);

    public static void writeResponse(HttpServletResponse response, JSONObject json) throws Exception {
        if (null == response || null == json) {
            return;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static String getContentFromResponseOutputStream(HttpServletResponse response) throws Exception {
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            Class<?> outputStreamClazz = outputStream.getClass();
            // 取出流对象中的OutputBuffer对象，该对象记录响应到客户端的内容
            Field obField = outputStreamClazz.getDeclaredField("ob");
            if (obField.getType().toString().endsWith("OutputBuffer")) {
                obField.setAccessible(true);
                Object objectBuffer = obField.get(outputStream);
                Class<?> outputBufferClazz = objectBuffer.getClass();
                Field outputChunkField = outputBufferClazz.getDeclaredField("outputChunk");
                outputChunkField.setAccessible(true);
                if (outputChunkField.getType().toString().endsWith("ByteChunk")) {
                    Object byteChunkObject = outputChunkField.get(objectBuffer);// 取到byte流
                    byte[] responseBytes = (byte[]) byteChunkObject.getClass().getDeclaredMethod("getBytes").invoke(byteChunkObject);
                    return EncodingUtils.getString(responseBytes, "UTF-8");
                }
            }
            return null;
        } catch (Exception e) {
            logger.error("从HttpServletResponse获取服务响应内容失败", e);
            return null;
        }
    }


    public static JSONObject newErrorWithMetaReturnJson(String code, String msg, JSONObject meta) throws Exception {
        JSONObject responseJson = new JSONObject();
        responseJson.put("code", code);
        responseJson.put("msg", ValueUtils.getNotNull(msg));
        JSONObject resultJson = new JSONObject();
        responseJson.put("result", resultJson);
        resultJson.put("meta", ValueUtils.getNotNull(meta));
        return responseJson;
    }

    public static JSONObject newErrorWithMetaReturnJson(String msg, JSONObject meta) throws Exception {
        return newErrorWithMetaReturnJson(ResponseCode.DEFAULT_CODE_FAILURE, msg, meta);
    }

    public static JSONObject newErrorReturnJson(String code, String msg) throws Exception {
        JSONObject resJson = new JSONObject();
        resJson.put("code", code);
        resJson.put("msg", ValueUtils.getNotNull(msg));
        return resJson;
    }

    public static JSONObject newErrorReturnJson(String msg) throws Exception {
        return newErrorReturnJson(ResponseCode.DEFAULT_CODE_FAILURE, msg);
    }

    public static JSONObject newTableReturnJson(JSONObject meta, JSONObject data, JSONObject page, JSONObject table,
                                                String msg) throws Exception {
        JSONObject responseJson = new JSONObject();
        responseJson.put("code", ResponseCode.CODE_SUCCESS);
        responseJson.put("msg", ValueUtils.getNotNull(msg, ResponseCode.MSG_SUCCESS));

        JSONObject resultJson = new JSONObject();
        responseJson.put("result", resultJson);

        JSONObject curData = data;
        if (null == curData) {
            curData = new JSONObject();
        }
        if (null != table) {
            curData.put("table", table);
        }
        resultJson.put("data", curData);
        resultJson.put("meta", ValueUtils.getNotNull(meta));
        resultJson.put("page", ValueUtils.getNotNull(page));

        return responseJson;
    }

    public static JSONObject newReturnJson(JSONObject meta, JSONObject data, JSONObject page, JSONArray list,
                                           String msg) throws Exception {
        JSONObject responseJson = new JSONObject();
        responseJson.put("code", ResponseCode.CODE_SUCCESS);
        responseJson.put("msg", ValueUtils.getNotNull(msg, ResponseCode.MSG_SUCCESS));

        JSONObject resultJson = new JSONObject();
        responseJson.put("result", resultJson);

        JSONObject curData = data;
        if (null == curData) {
            curData = new JSONObject();
        }
        if (null != list) {
            curData.put("list", list);
        }
        resultJson.put("data", curData);
        resultJson.put("meta", ValueUtils.getNotNull(meta));
        resultJson.put("page", ValueUtils.getNotNull(page));

        return responseJson;
    }

    public static JSONObject newReturnJson(JSONObject meta, JSONObject data, JSONObject page, JSONArray list, Integer code, String msg)
            throws Exception {
        JSONObject responseJson = new JSONObject();
        responseJson.put("code", code);
        responseJson.put("msg", ValueUtils.getNotNull(msg, ResponseCode.MSG_SUCCESS));

        JSONObject resultJson = new JSONObject();
        responseJson.put("result", resultJson);

        JSONObject curData = data;
        if (null == curData) {
            curData = new JSONObject();
        }
        if (null != list) {
            curData.put("list", list);
        }
        resultJson.put("data", curData);
        resultJson.put("meta", ValueUtils.getNotNull(meta));
        resultJson.put("page", ValueUtils.getNotNull(page));

        return responseJson;
    }

}
