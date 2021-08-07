package View.Messenger;

import Config.Config;
import Controller.DataBase.DataBase;
import Model.User;
import View.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GroupMessageViewer implements Initializable {
    @FXML
    private Label reciversLabel;

    @FXML
    private TextField userName;

    @FXML
    private TextField groupName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataBase.getDB().newChatUsers();
        setRecivers();
    }

    @FXML
    void Return() throws IOException {
        MainView.getMV().back();
    }

    @FXML
    void addAllUsers() {
        for(User u : DataBase.getDB().getAllUsers()){
            DataBase.getDB().addChatUsers(u);
        }
        setRecivers();
    }

    @FXML
    void addFromGroup() {
        if(DataBase.getDB().getCurrentUser().getGroup(groupName.getText()) != null) {
            for(String u : DataBase.getDB().getCurrentUser().getGroup(groupName.getText()).getUsers())
                DataBase.getDB().addChatUsers(DataBase.getDB().getUser(u));
        }
        setRecivers();
    }

    @FXML
    void addFromUsers() {
        if(DataBase.getDB().getUser(userName.getText()) != null)DataBase.getDB().addChatUsers(DataBase.getDB().getUser(userName.getText()));
        setRecivers();
    }

    @FXML
    void send() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"newMessage"));
    }

    public void setRecivers(){
        String text = "Receivers : ";
        for(User u : DataBase.getDB().getChatUsers()){
            text += u.getUserName();
            text += ", ";
        }
        reciversLabel.setText(text);
    }
}
