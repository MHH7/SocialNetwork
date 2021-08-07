package View.ChatRoom;

import Config.Config;
import Controller.DataBase.DataBase;
import Event.Texts.Message.NewMessageEvent;
import Listener.ChatRoomListener;
import Model.Message;
import View.MainView;
import View.Profile.MiniProfileViewer;
import View.Texts.Message.MiniMessageViewer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatRoomViewer implements Initializable {
    private String image;
    private ChatRoomListener chatRoomListener;

    @FXML
    private VBox usersVBox;

    @FXML
    private TextField userTextBox;

    @FXML
    private VBox messagesVBox;

    @FXML
    private TextArea messageTextBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatRoomListener = new ChatRoomListener();
        showUsers();
        showMessages();
    }

    @FXML
    void Return() throws IOException {
        MainView.getMV().back();
    }

    @FXML
    void addImage() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterPNG);
        File file = fileChooser.showOpenDialog(null);
        image = file.getAbsolutePath();
        DataBase.getDB().Save();
    }

    @FXML
    void addNewMember() {
        if(userTextBox.getText() != null)chatRoomListener.addUser(userTextBox.getText());
        showUsers();
    }

    @FXML
    void leaveGroup() throws IOException{
        chatRoomListener.leaveGroup(DataBase.getDB().getCurrentUser().getUserName());
        Return();
    }

    @FXML
    void send() {
        NewMessageEvent event = new NewMessageEvent(messageTextBox.getText(),image);
        chatRoomListener.addMessage(event);
        showMessages();
    }

    public void showUsers(){
        usersVBox.getChildren().clear();
        ArrayList<String> users = DataBase.getDB().getCurrentChatRoom().getUsers();
        for(String u : users){
            MiniProfileViewer miniProfileViewer = new MiniProfileViewer(DataBase.getDB().getUser(u));
            usersVBox.getChildren().add(miniProfileViewer.getPane());
        }
    }

    public void showMessages(){
        ArrayList<Integer> messages = DataBase.getDB().getCurrentChatRoom().getMessages();
        for(int m : messages){
            MiniMessageViewer miniMessageViewer = new MiniMessageViewer((Message) DataBase.getDB().getText(m));
            messagesVBox.getChildren().add(miniMessageViewer.getPane());
        }
    }

    @FXML
    void home() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"mainPage"));
    }
}
