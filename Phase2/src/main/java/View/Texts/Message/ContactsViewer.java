package View.Texts.Message;

import Listener.MessengerListener;
import Model.User;
import View.MainView;
import View.Messenger.MessengerViewer;
import View.Profile.MiniProfileViewer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ContactsViewer implements Initializable {
    private MessengerListener messengerListener;

    @FXML
    private VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messengerListener = new MessengerListener();
        ArrayList<User> contacts = messengerListener.getContacts();
        for(User user : contacts){
            MiniProfileViewer miniProfileViewer = new MiniProfileViewer(user);
            vBox.getChildren().add(miniProfileViewer.getPane());
        }
    }

    @FXML
    void Return() throws IOException {
        MainView.getMV().back();
    }
}
