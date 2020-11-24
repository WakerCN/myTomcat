package com.weiwei.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: Mine类型map
 *
 * @author Waker
 * @createData 2020-11-23 周一 22:59
 */
public class MimeType {

    private static final Map<String, String> mimeMap = new HashMap<>();

    static {
        mimeMap.put("", "text/plain");
        mimeMap.put("txt", "text/plain");
        mimeMap.put("html", "text/html");
        mimeMap.put("css", "text/css");
        mimeMap.put("js", "application/x-javascript");
        mimeMap.put("json", "application/json");
        mimeMap.put("jpg", "image/jpeg");
        mimeMap.put("png", "image/png");
    }

    public static String getMineType(String fileType) {
        return mimeMap.get(fileType);
    }

}
