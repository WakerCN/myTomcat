package com.weiwei.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Description: properties配置文件，工具类，读取配置文件的内容
 *
 * @author Waker
 * @createData 2020-11-21 周六 10:15
 */
public class PropertiesManager {

    private final Properties properties;

    private static final Logger logger = LogManager.getLogger(PropertiesManager.class);

    public PropertiesManager(String propFile) throws FileNotFoundException {
        // 从配置文件加载
        this.properties = new Properties();
        URL resource = PropertiesManager.class.getClassLoader().getResource(propFile);
        if (resource == null) {
            throw new FileNotFoundException("未找到配置文件" + propFile);
        }
        String path = resource.getPath();
        try {
            properties.load(new FileReader(path));
        } catch (IOException e) {
            logger.error("配置文件加载", e);
        }
    }

    /**
     * 获取当前配置文件中配置的值
     *
     * @param key 属性的key
     * @return 返回key属性对应的值
     */
    public String get(String key) {
        return this.properties.getProperty(key);
    }

}
