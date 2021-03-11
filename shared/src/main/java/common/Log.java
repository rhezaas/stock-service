package common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
    private static Logger log = LoggerFactory.getLogger(Log.class);

    public static void info(Object message) {
        log.info("{}", message);
    }

    public static void error(Object message) {
        log.error("{}", message);
    }
}
