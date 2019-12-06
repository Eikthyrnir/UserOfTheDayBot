import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerTest {

    private static final Logger logger = LoggerFactory.getLogger(LoggerTest.class);


    public static void main(String[] args) {

        String msg = "Hello, world!";

        for (int i = 0; i < 5; i++) {
            new Thread(()-> {
                new Thread(()-> {
                    logger.debug(msg);
                }).start();
                logger.info(msg);
            }).start();
        }
        logger.error(msg);
        logger.warn (msg);
    }

}
