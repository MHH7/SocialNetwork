package View.PersonalPage;

import Config.Config;
import Controller.DataBase.DataBase;
import Listener.PersonalPageListener;
import View.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PersonalPageViewer implements Initializable {
    private PersonalPageListener personalPageListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        personalPageListener = new PersonalPageListener();
    }

    @FXML
    void Return() throws IOException {
        MainView.getMV().back();
    }

    @FXML
    void changeInfo() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"privacySettings"));
    }

    @FXML
    void newTweet() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"newTweet"));
    }

    @FXML
    void notifications() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"notifications"));
    }

    @FXML
    void oldTweets() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"tweetList"));
    }

    @FXML
    void requests() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"requestList"));
    }

    @FXML
    void showLists() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"lists"));
    }

    @FXML
    void showPersonalInformation() throws IOException{
        DataBase.getDB().setProfileUser(DataBase.getDB().getCurrentUser());
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"profile"));
    }
}
