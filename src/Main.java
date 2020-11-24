import com.weiwei.staticServer.StaticServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Description: 程序入口
 *
 * @author Waker
 * @createData 2020-11-20 周五 22:21
 */
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        StaticServer staticServer = null;
        try {
            staticServer = new StaticServer();
            staticServer.start();
        } catch (Exception e) {
            logger.error("服务器启动失败", e);
        }

        logger.info("main程序结束");
    }

}
