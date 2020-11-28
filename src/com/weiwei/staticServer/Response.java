package com.weiwei.staticServer;

import com.weiwei.utils.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Description: 响应类，响应给服务器对应的信息
 *
 * @author Waker
 * @createData 2020-11-23 周一 14:01
 */
public class Response {

    private static final Logger logger = LogManager.getLogger(Response.class);
    private static final String BLANK = " ";
    private static final String CRLF = "\r\n";

    private final OutputStream os;
    private final StringBuilder responseInfo;
    private String mimeType;
    private byte[] resBody;

    public Response(OutputStream os) {
        this.os = os;
        responseInfo = new StringBuilder();
        mimeType = "text/plain";
    }

    /**
     * 根据响应内容和状态码推送到服务服务器
     *
     * @param code 状态码
     */
    public void pushToBrowser(int code) {
        buildHeadInfo(code);
        try {
            os.write(responseInfo.toString().getBytes());
            if (resBody != null) {
                os.write(resBody);
            }
            logger.debug("向浏览器发送数据");
        } catch (IOException e) {
            logger.error("响应失败，os写失败", e);
        }
    }

    /**
     * 设置响应体
     *
     * @param content 响应内容
     */
    public void setContent(String content) {
        mimeType = "text/plain";
        resBody = content.getBytes();
    }

    /**
     * 设置响应体
     *
     * @param file 文件
     */
    public void setContent(File file) {
        // 1.根据文件类型设置mimeType
        setMimeType(FileUtils.getMimeType(file));
        System.out.println("mimeType = " + mimeType);
        // 2.将文件读入内存，os输出到浏览器
        resBody = FileUtils.fileToByteArray(file);
    }

    /**
     * 设置响应的mime类型
     *
     * @param mimeType mime类型
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * 构建响应头
     * HTTP/2 200 OK
     * Content-Type: image/png
     * Content-Disposition:attachment;filename="A.png"
     *
     * @param code 响应码
     */
    private void buildHeadInfo(int code) {
        // 构建响应行
        responseInfo.append("HTTP/1.1").append(BLANK);
        responseInfo.append(code).append(BLANK);
        responseInfo.append(CodeStatus.getStatus(code)).append(CRLF);
        // 构建响应头
        responseInfo.append("Content-Type: ").append(mimeType).append(";charset=UTF-8").append(CRLF);
        // 构建响应空行
        responseInfo.append(CRLF);
    }

}
