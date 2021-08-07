package View.ChatRoom;

import Config.Config;
import Controller.DataBase.DataBase;
import Controller.Pages.Login;
import Model.ChatRoom;
import View.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class MiniChatRoomViewer {
    private final ChatRoom chatRoom;
    private Label label;
    private Button button;
    private Pane pane;
    static private final Logger logger = LogManager.getLogger(MiniChatRoomViewer.class);

    public MiniChatRoomViewer(ChatRoom chatRoom){
        this.chatRoom = chatRoom;
        start();
    }

    private void start(){
        startLabel();
        startButton();
    }

    private void startLabel(){
        label = new Label();
        label.setAlignment(Pos.TOP_LEFT);
        label.setLayoutX(0);
        label.setLayoutY(0);
        label.setText(chatRoom.getName() + " : " + chatRoom.getUsers().size() + " Members");
        label.setVisible(true);
    }

    private void startButton(){
        button = new Button();
        button.setText("Show ChatRoom");
        button.setLayoutX(0);
        button.setLayoutY(50);
        button.setVisible(true);
        button.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DataBase.getDB().setCurrentChatRoom(chatRoom);
                try {
                    MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"chatRoom"));
                } catch (IOException e) {
                    logger.error("Chat Room FXML file did not found!");
                }
            }
        });
    }


    public Label getLabel() {
        return label;
    }

    public Button getButton() {
        return button;
    }

    public Pane getPane(){
        pane = new Pane();
        pane.getChildren().add(label);
        pane.getChildren().add(button);
        pane.setVisible(true);
        return pane;
    }
}
