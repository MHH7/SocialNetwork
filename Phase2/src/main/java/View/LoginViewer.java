package View;

import Config.Config;
import Event.Login.LoadAccountEvent;
import Event.Login.NewAccountEvent;
import Listener.LoginListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewer implements Initializable {

    private LoginListener loginListener;

    @FXML
    private TextField nameText;

    @FXML
    private TextField userNameText1;

    @FXML
    private TextField passwordText1;

    @FXML
    private TextField birthDateText;

    @FXML
    private TextField emailText;

    @FXML
    private TextField phoneNumbertext;

    @FXML
    private TextField IDText;

    @FXML
    private TextArea bioText;

    @FXML
    private TextField userNameText2;

    @FXML
    private TextField passwordText2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginListener = new LoginListener(this);
    }

    @FXML
    void showLoadAccount() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"loadAccount"));
    }

    @FXML
    void showNewAccount() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"newAccount"));
    }

    @FXML
    void createNewAccount() throws IOException{
        if(loginListener.createNewAccount(new NewAccountEvent(nameText.getText(),userNameText1.getText(),passwordText1.getText(),
                birthDateText.getText(),emailText.getText(),phoneNumbertext.getText(),bioText.getText(), IDText.getText()))){
            MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"mainPage"));
        }
    }

    @FXML
    void loadAccount() throws IOException{
        if(loginListener.loadAccount(new LoadAccountEvent(userNameText2.getText(),passwordText2.getText()))){
            MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"mainPage"));
        }
    }

    @FXML
    void Return() throws IOException{
        MainView.getMV().back();
    }
}
