import Logic.DataBase;
import Logic.Pages.Login;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main {
    static private final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        logger.info("Program started!");
        DataBase.getDB().start();
    }

}
