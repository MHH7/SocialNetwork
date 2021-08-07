package View.Profile;

import Config.Config;
import Controller.DataBase.DataBase;
import Event.Profile.BlockEvent;
import Event.Profile.FollowEvent;
import Event.Profile.ReportEvent;
import Listener.ProfileListener;
import Model.User;
import View.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainProfileViewer implements Initializable {
    private User user;
    private ProfileListener profileListener;

    @FXML
    private Button blockButton;

    @FXML
    private Button followButton;

    @FXML
    private ImageView imagebox;

    @FXML
    private Label label;

    public User getUser() {
        return user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = DataBase.getDB().getProfileUser();
        followButton.setVisible(false);
        if (!DataBase.getDB().getCurrentUser().getUserName().equals(user.getUserName())
                && !DataBase.getDB().doesBlock(user.getUserName(), DataBase.getDB().getCurrentUser().getUserName()))
            followButton.setVisible(true);
        if(DataBase.getDB().doesFollow(DataBase.getDB().getCurrentUser().getUserName(),user.getUserName()))
        {
            followButton.setText("Unfollow");
        }
        if(DataBase.getDB().doesBlock(DataBase.getDB().getCurrentUser().getUserName(),user.getUserName()))
            blockButton.setText("Unblock");
        if(DataBase.getDB().getCurrentUser().getUserName().equals(user.getUserName()))blockButton.setVisible(false);
        profileListener = new ProfileListener(this);
        profileListener.start(imagebox,label);
    }

    @FXML
    void block() {
        BlockEvent event;
        if(blockButton.getText().equals("Block")) {
            event = new BlockEvent(DataBase.getDB().getCurrentUser(), user,true);
            blockButton.setText("Unblock");
        }else {
            event = new BlockEvent(DataBase.getDB().getCurrentUser(), user,false);
            blockButton.setText("Block");
        }
        profileListener.block(event);
    }

    @FXML
    void follow() {
        FollowEvent event;
        if(followButton.getText().equals("Follow")) {
            event = new FollowEvent(DataBase.getDB().getCurrentUser(), user,true);
            followButton.setVisible(false);
        }
        else{
            event = new FollowEvent(DataBase.getDB().getCurrentUser(), user,false);
            followButton.setText("Follow");
        }
        profileListener.follow(event);
    }

    @FXML
    void report() {
        ReportEvent event = new ReportEvent(DataBase.getDB().getCurrentUser(),user);
        profileListener.report(event);
    }

    @FXML
    void showChat() throws IOException{
        DataBase.getDB().newChatUsers();
        DataBase.getDB().addChatUsers(user);
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"chat"));
    }

    @FXML
    void Return() throws IOException {
        MainView.getMV().back();
    }
}
