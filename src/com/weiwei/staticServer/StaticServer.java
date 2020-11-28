package com.weiwei.staticServer;

import com.weiwei.utils.PropertiesManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Description: 静态服务器
 *
 * @author Waker
 * @since 2020-11-20 周五 21:52
 */
public class StaticServer {

    private static final Logger logger = LogManager.getLogger(StaticServer.class);
    private static int port;
    private static int count = 0;
    private static ServerSocket server;

    public StaticServer() throws Exception {
        PropertiesManager pm;
        String propFile = "staticServer.properties";
        try {
            pm = new PropertiesManager(propFile);
        } catch (FileNotFoundException e) {
            logger.error("配置文件" + propFile + "没找到", e);
            throw new Exception("无法读取配置文件，无法创建服务器");
        }
        String portStr = pm.get("port");
        if (portStr == null) {
            throw new Exception("配置文件中无port属性无法读取，无法创建服务器");
        }
        port = Integer.parseInt(portStr);
    }

    /**
     * 启动服务器
     */
    public void start() {
        logger.debug("服务器准备开始启动...");
        try {
            server = new ServerSocket(port);
            logger.info("服务启动成功，端口:" + port);
        } catch (IOException e) {
            logger.error("服务器启动失败", e);
            close();
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(10);

        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                Socket socket = server.accept();
                logger.debug("接收到socket" + ++count);
                executor.execute(new ConnectRun(socket));
            } catch (IOException e) {
                logger.error("socket接收失败", e);
            }
        }
    }


    /**
     * 关闭服务器流
     */
    public void close() {
        try {
            if (server != null) {
                server.close();
            }
        } catch (IOException e) {
            logger.error("服务器关闭失败", e);
        }
    }

}
