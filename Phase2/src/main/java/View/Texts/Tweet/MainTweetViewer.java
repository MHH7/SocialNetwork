package View.Texts.Tweet;

import Config.Config;
import Controller.DataBase.DataBase;
import Event.Texts.Tweet.LikeEvent;
import Event.Texts.Tweet.SaveEvent;
import Listener.Texts.TweetListener;
import Model.Tweet;
import View.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainTweetViewer implements Initializable {
    private Tweet tweet;
    private TweetListener tweetListener;

    @FXML
    private ImageView imagebox;

    @FXML
    private Label label;

    @FXML
    private TextField forwardTo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tweetListener = DataBase.getDB().getTweetHandler().getTweetListener();
        tweet = DataBase.getDB().getTweetHandler().getTweetListener().getCurrentTweet();
        if(!(tweet.getImage() == null)) {
            try {
                imagebox.setImage(new Image(tweet.getImagePhoto()));
            } catch (MalformedURLException e) {
            }
        }
        label.setText(tweet.open());
    }

    @FXML
    void Return() throws IOException {
        DataBase.getDB().getTweetHandler().back();
        MainView.getMV().back();
    }

    @FXML
    void addComment() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"newComment"));
    }

    @FXML
    void forward() {
        if(!(DataBase.getDB().getUser(forwardTo.getText()) == null))tweet.forward(forwardTo.getText());
    }

    @FXML
    void like() {
        LikeEvent event = new LikeEvent(tweet);
        tweetListener.like(event);
    }

    @FXML
    void reTweet() {
        tweet.reTweet();
    }

    @FXML
    void save() {
        SaveEvent event = new SaveEvent(tweet);
        tweetListener.save(event);
    }

    @FXML
    void showComments() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"tweetList"));
    }

    @FXML
    void Report() throws IOException{
        tweet.reprt();
        if(tweet.getReports() == 3)Return();
    }
}
