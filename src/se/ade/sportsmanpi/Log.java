package se.ade.sportsmanpi;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created with IntelliJ IDEA.
 * User: ade
 * Date: 2013-08-02
 * Time: 22:53
 * To change this template use File | Settings | File Templates.
 */
public class Log {
    private static Logger logger;

    public static void setup() {
        try {
            logger = Logger.getLogger("Main");
            logger.setLevel(Level.INFO);
            FileHandler fileTxt = new FileHandler("log.txt", true);
            SimpleFormatter formatterTxt = new SimpleFormatter();
            fileTxt.setFormatter(formatterTxt);
            logger.addHandler(fileTxt);
        } catch (IOException e) {
            System.out.println("Warning: Unable to open log");
        }
    }

    public static void d(String msg) {
        //System.out.println(msg);
        logger.log(Level.INFO, msg);
    }
}
