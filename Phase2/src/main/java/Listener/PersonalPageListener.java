package Listener;

import Controller.Pages.PersonalPage;
import Model.User;
import View.Profile.MiniProfileViewer;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class PersonalPageListener {
    private final PersonalPage personalPage;

    public PersonalPageListener(){
        personalPage = new PersonalPage();
    }

    public void showFollowings(VBox vbox){
        ArrayList<User> followings = personalPage.getFollowings();
        for(User user : followings){
            MiniProfileViewer miniProfileViewer = new MiniProfileViewer(user);
            vbox.getChildren().add(miniProfileViewer.getPane());
        }
    }

    public void showBlackList(VBox vbox){
        ArrayList<User> blacklist = personalPage.getBlackList();
        for(User user : blacklist){
            MiniProfileViewer miniProfileViewer = new MiniProfileViewer(user);
            vbox.getChildren().add(miniProfileViewer.getPane());
        }
    }

    public void showFollowers(VBox vBox){
        ArrayList<User> followers = personalPage.getFollowers();
        for(User user : followers){
            MiniProfileViewer miniProfileViewer = new MiniProfileViewer(user);
            vBox.getChildren().add(miniProfileViewer.getPane());
        }
    }
}
