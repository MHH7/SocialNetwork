package View.Texts.Tweet;

import Config.Config;
import Controller.DataBase.DataBase;
import Controller.Pages.Login;
import Listener.Texts.TweetListener;
import Model.Tweet;
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

public class MiniTweetViewer{
    private final Tweet tweet;
    private ImageView imageView;
    private Label label;
    private Button openButton;
    private Button profileButton;
    private Pane pane;
    static private final Logger logger = LogManager.getLogger(MiniTweetViewer.class);

    public MiniTweetViewer(Tweet tweet){
        this.tweet = tweet;
        start();
    }

    private void start(){
        startImage();
        startLabel();
        startOpenButton();
        startProfileButton();
    }

    private void startImage(){
        User user = DataBase.getDB().getUser(tweet.getUser());
        imageView = new ImageView();
        if(!(user.getProfilePath() == null)) {
            try {
                imageView.setImage(new Image(user.getProfilePhoto()));
            } catch (MalformedURLException e) {
                logger.error("Profile image photo did not found!");
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
        label.setText(tweet.show());
        label.setVisible(true);
    }

    private void startOpenButton(){
        openButton = new Button();
        openButton.setText("Open Tweet");
        openButton.setLayoutX(100);
        openButton.setLayoutY(0);
        openButton.setVisible(true);
        openButton.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TweetListener tweetListener = new TweetListener();
                tweetListener.setCurrentTweet(tweet);
                tweetListener.setTweets(tweet.getComments());
                DataBase.getDB().getTweetHandler().getStack().push(tweetListener);
                try {
                    MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"tweet"));
                } catch (IOException e) {
                    logger.error("Tweet FXML file did not found!");
                }
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
                DataBase.getDB().setProfileUser(DataBase.getDB().getUser(tweet.getUser()));
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
        pane.getChildren().add(openButton);
        pane.getChildren().add(profileButton);
        pane.getChildren().add(imageView);
        pane.setVisible(true);
        return pane;
    }
}
