package View.Messenger;

import Config.Config;
import Controller.DataBase.DataBase;
import Listener.MessengerListener;
import View.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MessengerViewer implements Initializable {
    private MessengerListener messengerListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messengerListener = new MessengerListener();
    }

    @FXML
    void Return() throws IOException {
        MainView.getMV().back();
    }

    @FXML
    void groupMessage() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"groupMessage"));
    }

    @FXML
    void inbox() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"inbox"));
    }

    @FXML
    void contacts() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"contacts"));
    }

    @FXML
    void savedMessages() throws IOException{
        DataBase.getDB().newChatUsers();
        DataBase.getDB().addChatUsers(DataBase.getDB().getCurrentUser());
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"chat"));
    }

    @FXML
    void chatRoomManager() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"chatRoomManager"));
    }
}
