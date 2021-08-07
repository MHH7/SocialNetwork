package View.Texts.Message;

import Config.Config;
import Controller.DataBase.DataBase;
import Controller.Pages.Login;
import Model.Message;
import Model.User;
import View.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;

public class MiniMessageViewer {
    private final Message message;
    private ImageView imageView;
    private Label label;
    private Button saveButton;
    private Button openButton;
    private Button profileButton;
    private Pane pane;
    static private final Logger logger = LogManager.getLogger(MiniMessageViewer.class);

    public MiniMessageViewer(Message message){
        this.message = message;
        start();
    }

    private void start(){
        startImage();
        startLabel();
        startProfileButton();
        startSaveButton();
        startOpenButton();
    }

    private void startImage(){
        User user = DataBase.getDB().getUser(message.getSender());
        imageView = new ImageView();
        if(!(user.getProfilePath() == null)) {
            try {
                imageView.setImage(new Image(user.getProfilePhoto()));
            } catch (MalformedURLException e) {
            }
        }
        Circle circle = new Circle(25,25,25);
        imageView.setClip(circle);
        imageView.setLayoutX(0);
        imageView.setLayoutY(0);
        imageView.setVisible(true);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
    }

    private void startLabel(){
        label = new Label();
        label.setAlignment(Pos.TOP_LEFT);
        label.setLayoutX(200);
        label.setLayoutY(0);
        label.setText(message.show());
        label.setVisible(true);
    }

    public void startOpenButton(){
        openButton = new Button();
        openButton.setText("Open Message");
        openButton.setLayoutX(100);
        openButton.setLayoutY(25);
        openButton.setVisible(true);
        openButton.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DataBase.getDB().setCurrentMessage(message);
                try {
                    MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"message"));
                } catch (IOException e) {
                    logger.error("Message FXML file did not found!");
                }
            }
        });
    }

    private void startSaveButton(){
        saveButton = new Button();
        saveButton.setText("Save Message");
        saveButton.setLayoutX(100);
        saveButton.setLayoutY(0);
        saveButton.setVisible(true);
        saveButton.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                message.save();
            }
        });
    }

    public void startProfileButton(){
        profileButton = new Button();
        profileButton.setText("Show Profile");
        profileButton.setLayoutX(100);
        profileButton.setLayoutY(50);
        profileButton.setVisible(true);
        profileButton.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DataBase.getDB().setProfileUser(DataBase.getDB().getUser(message.getSender()));
                try {
                    MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"profile"));
                } catch (IOException e) {
                    logger.error("Profile FXML file did not found!");
                }
            }
        });
    }

    public Pane getPane(){
        pane = new Pane();
        pane.getChildren().add(label);
        pane.getChildren().add(saveButton);
        pane.getChildren().add(profileButton);
        pane.getChildren().add(openButton);
        pane.getChildren().add(imageView);
        pane.setVisible(true);
        return pane;
    }
}
