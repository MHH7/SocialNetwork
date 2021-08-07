package Listener;

import Controller.DataBase.DataBase;
import Controller.Pages.Login;
import Controller.Pages.Profile;
import Event.Profile.BlockEvent;
import Event.Profile.FollowEvent;
import Event.Profile.ReportEvent;
import Model.PT;
import Model.User;
import View.Profile.MainProfileViewer;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;

public class ProfileListener {
    private final Profile profile;
    private final MainProfileViewer mainProfileViewer;
    static private final Logger logger = LogManager.getLogger(ProfileListener.class);

    public ProfileListener(MainProfileViewer mainProfileViewer){
        this.mainProfileViewer = mainProfileViewer;
        profile = new Profile();
    }

    public void follow(FollowEvent event){
        if(event.getState()) profile.follow(event.getFollower(),event.getReciever());
        else profile.unFollow(event.getFollower(),event.getReciever());
    }

    public void report(ReportEvent event){
        profile.report(event.getReporter(), event.getReciever());
    }

    public void block(BlockEvent event){
        if(event.getState())profile.block(event.getBlocker(), event.getReciever());
        else profile.unBlock(event.getBlocker(), event.getReciever());
    }

    public void start(ImageView imageView, Label label){
        Image image = null;
        try {
            if(!(DataBase.getDB().getProfileUser().getProfilePath() == null))
                image = new Image(DataBase.getDB().getProfileUser().getProfilePhoto());
        } catch (MalformedURLException e) {
            logger.info("Profile image file didn't found!");
        }
        imageView.setImage(image);

        String text = "";

        User user = mainProfileViewer.getUser();
        text += ("Name : " + user.getName());
        text += "\n";
        text += ("Username : " + user.getUserName());
        text += "\n";
        if(user.getEmailPT().equals(PT.Public)){
            text += ("Email : " + user.getEmail());
            text += "\n";
        }
        if(user.getPhoneNumberPT().equals(PT.Public)) {
            text += ("Phone Number : " + user.getPhoneNumber());
            text += "\n";
        }
        if(user.getBirthDatePT().equals(PT.Public)) {
            text += ("Birthdate : " + user.getBirthDate());
            text += "\n";
        }
        text += ("Bio : " + user.getBio());
        text += "\n";
        if(user.getLastSeenType().equals("All")) {
            text += ("Last seen : "  + user.getLastSeen());
            text += "\n";
        }
        else if(user.getLastSeenType().equals("Contacts")){
            if(DataBase.getDB().doesFollow(DataBase.getDB().getCurrentUser().getUserName(),user.getUserName())){
                if(user.isOnlie()) {
                    text += ("Online");
                    text += "\n";
                }
                else {
                    text += ("Last seen : " + user.getLastSeen());
                    text += "\n";
                }
            }
            else{
                text += ("Last seen recently");
                text += "\n";
            }
        }
        else {
            text += ("Last seen recently");
            text += "\n";
        }
        if(!user.equals(DataBase.getDB().getCurrentUser())) {
            if (DataBase.getDB().doesFollow(DataBase.getDB().getCurrentUser().getUserName(), user.getUserName())) {
                text += ("Followed");
                text += "\n";
            } else {
                text += ("Not Followed");
                text += "\n";
            }
        }

        label.setText(text);
    }
}
