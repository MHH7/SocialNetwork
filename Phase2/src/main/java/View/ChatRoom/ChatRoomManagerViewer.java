package View.ChatRoom;

import Controller.DataBase.DataBase;
import Listener.ChatRoomListener;
import Model.ChatRoom;
import Model.User;
import View.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatRoomManagerViewer implements Initializable {
    private ChatRoomListener chatRoomListener;

    @FXML
    private VBox vBox;

    @FXML
    private TextField newGroupName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatRoomListener = new ChatRoomListener();
        showChatRooms();
    }

    @FXML
    void Return() throws IOException {
        MainView.getMV().back();
    }

    @FXML
    void addNewGroup() {
        chatRoomListener.newGroup(newGroupName.getText());
        showChatRooms();
    }

    public void showChatRooms(){
        ArrayList<ChatRoom> chatRooms = DataBase.getDB().getChatRooms();
        vBox.getChildren().clear();
        if(chatRooms != null && chatRooms.size() != 0) {
            for (ChatRoom c : chatRooms) {
                if (c == null) continue;
                int p = 0;
                for(String u : c.getUsers())if(DataBase.getDB().getCurrentUser().getUserName().equals(u))p = 1;
                if(p == 0)continue;
                MiniChatRoomViewer miniChatRoomViewer = new MiniChatRoomViewer(c);
                vBox.getChildren().add(miniChatRoomViewer.getPane());
            }
        }
    }
}
