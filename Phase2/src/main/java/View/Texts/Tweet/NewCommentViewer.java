package View.Texts.Tweet;

import Controller.DataBase.DataBase;
import Event.Texts.Tweet.NewTweetEvent;
import Listener.Texts.TweetListener;
import View.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewCommentViewer implements Initializable {
    private String image;

    @FXML
    private TextArea textBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void addImage() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterPNG);
        File file = fileChooser.showOpenDialog(null);
        image = file.getAbsolutePath();
    }

    @FXML
    void create() throws IOException {
        if(!textBox.getText().equals("")) {
            TweetListener tweetListener = DataBase.getDB().getTweetHandler().getTweetListener();
            NewTweetEvent event = new NewTweetEvent(textBox.getText(), image);
            tweetListener.newComment(event);
            Return();
        }
    }

    @FXML
    void Return() throws IOException {
        MainView.getMV().back();
    }
}
