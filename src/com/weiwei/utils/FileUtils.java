package com.weiwei.utils;

import com.weiwei.staticServer.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

/**
 * Description: 文件工具类
 *
 * @author Waker
 * @createData 2020-11-23 周一 22:55
 */
public class FileUtils {

    private static final Logger logger = LogManager.getLogger(FileUtils.class);

    /**
     * 获取文件呢的mime类型
     * mime类型参考（#https://www.w3school.com.cn/media/media_mimeref.asp）
     *
     * @param file 文件
     * @return mime类型
     */
    public static String getMimeType(File file) {
        String fileType = getFileType(file);
        System.out.println("fileType = " + fileType);
        return MimeType.getMineType(fileType);
    }

    /**
     * 获取文件类型（后缀名）
     *
     * @param file 文件
     * @return 文件的后缀名
     */
    public static String getFileType(File file) {
        if (file == null) {
            throw new IllegalArgumentException("文件不存在");
        }
        String fileName = file.getName();
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        return fileName.substring(index + 1);
    }

    /**
     * 文件转字节数组
     *
     * @param file 文件
     * @return 如果文件不存在返回null
     */
    public static byte[] fileToByteArray(File file) {
        if (file == null) {
            return null;
        }

        byte[] datas = null;
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;

        try {
            fis = new FileInputStream(file);
            baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0 , len);
            }
            datas = baos.toByteArray();
        } catch (IOException e) {
            logger.error("文件" + file.getName() + "读取失败", e);
            return null;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                logger.error("fileInputStream 关闭失败,close()调用失败", e);
            }
        }
        return datas;
    }

}
