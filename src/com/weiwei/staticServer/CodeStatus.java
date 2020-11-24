package com.weiwei.staticServer;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 状态的描述
 *
 * @author Waker
 * @since 2020-11-23 周一 17:40
 */
public class CodeStatus {

    private static final String SUCC = "OK";
    private static final String NOT_FOUND = "NOT FOUND";
    private static final String SERVER_ERROR = "SERVER ERROR";

    private static final Map<Integer, String> codeMap = new HashMap<>();

    static {
        codeMap.put(200, SUCC);
        codeMap.put(404, NOT_FOUND);
        codeMap.put(500, SERVER_ERROR);
    }

    /**
     * 根据状态码获取状态码对应的描述
     * @param code 状态码
     * @return 返回状态码对应的描述
     */
    public static String getStatus(int code) {
        return codeMap.get(code);
    }

}
