import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerTest {

    private static final Logger logger = LoggerFactory.getLogger(LoggerTest.class);


    public static void main(String[] args) {

        String msg = "Hello, world!";
        logger.debug(msg);
        logger.info (msg);
        logger.error(msg);
        logger.warn (msg);
    }

}
