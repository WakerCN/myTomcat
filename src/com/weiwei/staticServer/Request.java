package com.weiwei.staticServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Description: 请求类，处理一次网络请求，并解析其中的信息
 *
 * @author Waker
 * @createData 2020-11-22 周日 22:51
 */
public class Request {

    private static final Logger logger = LogManager.getLogger(Request.class);
    private static final String CRLF = "\r\n";
    private static final String BLANK = " ";

    private String method;
    private String reqUrl;
    private String reqBody;
    private final Map<String, List<String>> paramsMap = new HashMap<>();

    public Request(InputStream is) {
        byte[] buf = new byte[1024 * 1024 * 2];
        int len = 0;
        try {
            len = is.read(buf);
        } catch (IOException e) {
            logger.error("读取输入流失败", e);
        }
        if (len > -1) {
            String requsetInfo = new String(buf, 0, len);
            praseRequsetInfo(requsetInfo);
        }
    }

    /**
     * 解析请求信息
     * GET /index.html HTTP/1.1
     *
     * @param requsetInfo 所有的请求消息
     */
    private void praseRequsetInfo(String requsetInfo) {
        String requsetLine = requsetInfo.substring(0, requsetInfo.indexOf(CRLF));
        // 获取请求的方法 get或者post
        method = requsetLine.substring(0, requsetInfo.indexOf("/")).trim().toLowerCase();
        // 截取请求的url路径
        int url_begin_index = requsetLine.indexOf(BLANK);
        int url_end_index = requsetLine.lastIndexOf(BLANK);
        reqUrl = requsetInfo.substring(url_begin_index, url_end_index).trim();
        int index = reqUrl.indexOf("?");
        String queryStr = null;
        // 截取出querystr
        if (index != -1 && index != reqUrl.length() - 1) {
            queryStr = reqUrl.substring(index + 1);
        }

        if ("post".equals(method)) {
            reqBody = requsetInfo.substring(requsetInfo.lastIndexOf(CRLF));
        } else {
            reqBody = null;
        }

        // 控制台答应参数信息
        logger.trace("rqLine: " + requsetLine);
        logger.trace("method: " + method);
        logger.trace("reqUrl: " + reqUrl);
        logger.trace("queStr: " + queryStr);
        logger.trace("reqBody: " + reqBody);

        praseReqUrl(queryStr);
    }

    /**
     * 解析请求地址栏
     * 例如
     * /index.html?username=waker&password=123
     * 将其存储到paramsMap中
     *
     * @param queryStr 请求url中的查询字符串username=waker&password=123
     */
    private void praseReqUrl(String queryStr) {
        if (queryStr != null) {
            String[] params;
            params = queryStr.split("&");
            for (String param : params) {
                String[] kv = param.split("=");
                if (kv.length == 2) {
                    String key = kv[0];
                    String value = kv[1];
                    if (!paramsMap.containsKey(key)) {
                        paramsMap.put(key, new ArrayList<>());
                    }
                    paramsMap.get(key).add(value);
                }
            }
        }
    }

    public String getMethod() {
        return method;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public String getReqBody() {
        return reqBody;
    }

    /**
     * 获取请求参数中一个属性对应的所有的属性
     *
     * @param key 请求参数中的属性名
     * @return 没有的话返回null
     */
    public String[] getParamValues(String key) {
        List<String> list = paramsMap.get(key);
        if (list == null || list.size() < 1) {
            return null;
        }
        return list.toArray(new String[0]);
    }

    public String getParamValue(String key) {
        String[] values = getParamValues(key);
        return values == null ? null : values[0];
    }

}
