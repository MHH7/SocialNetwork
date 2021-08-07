package View.Profile;

import Config.Config;
import Controller.DataBase.DataBase;
import Controller.Pages.Login;
import Model.User;
import View.ChatRoom.MiniChatRoomViewer;
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

public class MiniProfileViewer {
    private final User user;
    private ImageView imageView;
    private Label label;
    private Button button;
    private Pane pane;
    static private final Logger logger = LogManager.getLogger(MiniChatRoomViewer.class);

    public MiniProfileViewer(User user){
        this.user = user;
        start();
    }

    private void start(){
        startImage();
        startLabel();
        startButton();
    }

    private void startImage(){
        imageView = new ImageView();
        if(!(user.getProfilePath() == null)) {
            try {
                imageView.setImage(new Image(user.getProfilePhoto()));
            } catch (MalformedURLException e) {
                logger.error("Profile image file did not found!");
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
        label.setLayoutX(100);
        label.setLayoutY(0);
        label.setText(user.getUserName());
        label.setVisible(true);
    }

    private void startButton(){
        button = new Button();
        button.setText("Show Profile");
        button.setLayoutX(100);
        button.setLayoutY(50);
        button.setVisible(true);
        button.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DataBase.getDB().setProfileUser(user);
                try {
                    MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"profile"));
                } catch (IOException e) {
                    logger.error("Profile FXML file did not found!");
                }
            }
        });
    }

    public ImageView getImageView() {
        return imageView;
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
        pane.getChildren().add(imageView);
        pane.setVisible(true);
        return pane;
    }
}
