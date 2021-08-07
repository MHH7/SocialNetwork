package Logic.Pages;

import Logic.DataBase;
import Logic.Reader;
import Model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class MainPage {
    static private final Logger logger = LogManager.getLogger(MainPage.class);
    private final Explorer explorer;
    private final Messenger messenger;
    private final PersonalPage personalPage;
    private final Settings settings;
    private final TimeLine timeLine;

    public Settings getSettings() {
        return settings;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public MainPage(){
        messenger = new Messenger();
        personalPage = new PersonalPage();
        settings = new Settings();
        timeLine = new TimeLine();
        explorer = new Explorer(timeLine);
    }

    public void start(User user){
        while (true) {
            logger.info(user.getUserName() + " logged into account!");
            System.out.println("----------MainPage----------");
            System.out.println("1_PersonalPage");
            System.out.println("2_TimeLine");
            System.out.println("3_Explorer");
            System.out.println("4_Messenger");
            System.out.println("5_Settings");
            int x = 0;
            while (x < 1 || x > 5) {
                x = Reader.get().nextInt();
                if (x == 1) {
                    personalPage.start(user);
                } else if (x == 2) {
                    timeLine.start(user);
                } else if (x == 3) {
                    explorer.start(user);
                } else if (x == 4) {
                    messenger.start(user);
                } else if (x == 5) {
                    settings.start(user);
                } else {
                    System.out.println("Invalid Input!");
                }
                DataBase.getDB().Save();
            }
        }
    }
}
