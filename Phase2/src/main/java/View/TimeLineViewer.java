package View;

import Controller.DataBase.DataBase;
import Listener.TimeLineListener;
import Model.Tweet;
import View.Texts.Tweet.MiniTweetViewer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TimeLineViewer implements Initializable {
    private int index;
    private ArrayList<Integer> tweets;
    private TimeLineListener timeLineListener;

    @FXML
    private VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vBox.setVisible(true);
        index = 0;
        timeLineListener = new TimeLineListener();
        tweets = timeLineListener.getTweets();
        if(tweets.size() != 0){
            show(0);
        }
    }

    @FXML
    void next() {
        if(tweets.size() != 0){
            if(index == tweets.size() - 1){
                show(0);
                index = 0;
            }
            else{
                show(index + 1);
                index++;
            }
        }
    }

    @FXML
    void prev() {
        if(tweets.size() != 0){
            if(index == 0){
                index = tweets.size() - 1;
                show(index);
            }
            else{
                index--;
                show(index);
            }
        }
    }

    @FXML
    void Return() throws IOException{
        MainView.getMV().back();
    }

    public void show(int x){
        vBox.getChildren().clear();
        MiniTweetViewer miniTweetViewer = new MiniTweetViewer((Tweet) DataBase.getDB().getText(tweets.get(x)));
        vBox.getChildren().add(miniTweetViewer.getPane());
    }

}
