package View.Texts.Tweet;

import Controller.DataBase.DataBase;
import Model.Tweet;
import View.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TweetListViewer implements Initializable {
    private ArrayList<Integer> tweets;

    @FXML
    private VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tweets = DataBase.getDB().getTweetHandler().getTweetListener().getTweets();
        for(int i = 0;i < tweets.size();i++){
            Tweet temp = (Tweet) DataBase.getDB().getText(tweets.get(i));
            MiniTweetViewer miniTweetViewer = new MiniTweetViewer(temp);
            vBox.getChildren().add(miniTweetViewer.getPane());
        }
    }

    @FXML
    void Return() throws IOException {
        MainView.getMV().back();
    }
}
