package View.Texts.Message;

import Controller.DataBase.DataBase;
import Event.Texts.Message.NewMessageEvent;
import Listener.MessengerListener;
import Model.User;
import View.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewMessageViewer implements Initializable {
    private String image;
    private MessengerListener messengerListener;
    private ArrayList<User> users;

    @FXML
    private TextArea textBox;

    @FXML
    private Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        image = "";
        messengerListener = new MessengerListener();
        users = DataBase.getDB().getChatUsers();
        String text = "to : ";
        for(int i = 0;i < users.size();i++){
            text += users.get(i).getUserName();
            if(i != users.size() - 1)text += ", ";
        }
        label.setText(text);
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
    }

    @FXML
    void create() throws IOException{
        if(!textBox.getText().equals("")) {
            NewMessageEvent newMessageEvent = new NewMessageEvent(textBox.getText(),image);
            messengerListener.NewMessage(users,newMessageEvent);
            Return();
        }
    }
}
