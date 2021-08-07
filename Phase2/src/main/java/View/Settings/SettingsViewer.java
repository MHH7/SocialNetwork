package View.Settings;

import Config.Config;
import Controller.DataBase.DataBase;
import Event.Settings.DeleteAccountEvent;
import Event.Settings.LogOutEvent;
import Listener.Settings.SettingsListener;
import View.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsViewer implements Initializable {
    private SettingsListener settingsListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settingsListener = new SettingsListener(this);
    }

    @FXML
    void DeleteUser() throws IOException {
        DeleteAccountEvent event = new DeleteAccountEvent(DataBase.getDB().getCurrentUser());
        settingsListener.deleteAccount(event);
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"login"));
    }

    @FXML
    void LogOut() throws IOException{
        LogOutEvent event = new LogOutEvent(DataBase.getDB().getCurrentUser());
        settingsListener.logOutAccount(event);
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"login"));
    }

    @FXML
    void Return() throws IOException{
        MainView.getMV().back();
    }

    @FXML
    void privacySettings() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"privacySettings"));
    }
}
