package Graphic;

import Config.Config;
import Controller.DataBase.DataBase;
import View.MainView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main extends Application{
    static private final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("Program started!");
        DataBase.getDB().start();
        MainView.getMV().setMainStage(primaryStage);
        primaryStage.setResizable(false);
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"login"));
        primaryStage.show();
    }
}
