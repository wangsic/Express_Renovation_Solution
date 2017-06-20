package com.jzwy.zkx.product.rest.api.common.http;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RequestJsonWrapper {

    /**
     * 获取post的json格式的数据
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static JSONObject getPostJSONData(HttpServletRequest request) throws Exception {
        if (null == request || null == request.getMethod()) {
            return null;
        }
        String dataBodyJSONStr = null;
        if (!request.getMethod().equals("POST")) {
            return null;
        }
        dataBodyJSONStr = getStrFromPostRequestReader(request);
        JSONObject bodyJSON = JSONObject.parseObject(dataBodyJSONStr);
        return bodyJSON;
    }

    protected static String getStrFromPostRequestReader(HttpServletRequest request) throws Exception {
        if (null == request || !request.getMethod().equals("POST")) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = request.getReader();
            char[] charBuffer = new char[512];
            int bytesRead;
            while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
                builder.append(charBuffer, 0, bytesRead);
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
        return builder.toString();
    }

    protected byte[] getBytesFromRequestInputStream(HttpServletRequest request) throws Exception {
        if (null == request) {
            return null;
        }
        ByteArrayOutputStream baos = null;
        BufferedInputStream in = null;
        try {
            baos = new ByteArrayOutputStream();
            in = new BufferedInputStream(request.getInputStream());
            int i = 0;
            while ((i = in.read()) != -1) {
                baos.write(i);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != baos) {
                try {
                    baos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
