package View.Messenger;

import Config.Config;
import Controller.DataBase.DataBase;
import Listener.MessengerListener;
import Model.Message;
import Model.Text;
import Model.Tweet;
import View.MainView;
import View.Texts.Message.MiniMessageViewer;
import View.Texts.Tweet.MiniTweetViewer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatViewer implements Initializable {
    private ArrayList<Text> texts;
    private MessengerListener messengerListener;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messengerListener = new MessengerListener();
        texts = messengerListener.getChats();
        for(int i = 0;i < texts.size();i++){
            if(texts.get(i) instanceof Tweet){
                if(!(((Tweet)texts.get(i)).getUser().equals(DataBase.getDB().getCurrentUser().getUserName())))
                texts.get(i).setRead(true);
                MiniTweetViewer miniTweetViewer = new MiniTweetViewer((Tweet) texts.get(i));
                vBox.getChildren().add(miniTweetViewer.getPane());
            }
            else if(texts.get(i) instanceof Message){
                if(!(((Message)texts.get(i)).getSender().equals(DataBase.getDB().getCurrentUser().getUserName())))
                texts.get(i).setRead(true);
                MiniMessageViewer miniMessageViewer = new MiniMessageViewer((Message) texts.get(i));
                vBox.getChildren().add(miniMessageViewer.getPane());
            }
            DataBase.getDB().Save();
        }
        scrollPane.setVvalue(scrollPane.getVmax());
    }

    @FXML
    void Return() throws IOException {
        MainView.getMV().back();
    }

    @FXML
    void newMessage() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"newMessage"));
    }
}
