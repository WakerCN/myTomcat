package com.weiwei.staticServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Description: 每个socket类的处理
 *
 * @author Waker
 * @createData 2020-11-22 周日 22:18
 */
public class ConnectRun implements Runnable {

    private static final Logger logger = LogManager.getLogger(ConnectRun.class);
    private final Socket socket;
    private InputStream is;
    private OutputStream os;

    public ConnectRun(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            logger.trace("开始处理一次socket");
            is = socket.getInputStream();
            os = socket.getOutputStream();
        } catch (IOException e) {
            logger.error("socket请求，响应失败", e);
        }

        Request request = new Request(is);
        Response response = new Response(os);

        // TODO 处理业务逻辑

        response.setContent(new File("C:\\Users\\Waker\\Pictures\\Saved Pictures\\1.png"));
        response.pushToBrowser(200);
        close();
    }

    /**
     * 关闭socket中的流
     */
    private void close() {
        try {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            logger.error("socket流关闭失败", e);
        }
    }

}
