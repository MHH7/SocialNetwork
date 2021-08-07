package View.PersonalPage;

import Config.Config;
import Listener.PersonalPageListener;
import View.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListsViewer implements Initializable {
    private PersonalPageListener personalPageListener;

    @FXML
    private VBox vbox;

    @FXML
    private Button followersButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vbox.setAlignment(Pos.TOP_LEFT);
        personalPageListener = new PersonalPageListener();
    }

    @FXML
    void Return() throws IOException {
        MainView.getMV().back();
    }

    @FXML
    void blacklist() {
        vbox.getChildren().clear();
        personalPageListener.showBlackList(vbox);
    }

    @FXML
    void followers() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"contactsGroup"));
    }

    @FXML
    void followings() {
        vbox.getChildren().clear();
        personalPageListener.showFollowings(vbox);
    }
}
