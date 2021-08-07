package Controller.Pages;

import Controller.DataBase.DataBase;
import Model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Profile{
    static private final Logger logger = LogManager.getLogger(Profile.class);

    public void follow(User follower,User receiver){
        if (!DataBase.getDB().doesFollow(follower.getUserName(), receiver.getUserName()) &&
                !DataBase.getDB().doesBlock(follower.getUserName(), receiver.getUserName())) {
            Request r = new Request(follower, receiver);
            receiver.getRequests().add(r.getID());
        }
        logger.info(follower.getUserName() + " starts following " + receiver.getUserName());
        DataBase.getDB().Save();
    }

    public void unFollow(User follower,User receiver){
        for(int i = 0;i < follower.getFollowings().size();i++){
            if(follower.getFollowings().get(i).equals(receiver.getUserName())){
                follower.getFollowings().remove(i);
                break;
            }
        }
        for(int i = 0;i < receiver.getFollowers().size();i++){
            if(receiver.getFollowers().get(i).equals(receiver.getUserName())){
                receiver.getFollowers().remove(i);
                break;
            }
        }
        logger.info(follower.getUserName() + " unfollowed " + receiver.getUserName());
        DataBase.getDB().Save();
    }

    public void report(User reporter,User receiver){
        Notification N = new Notification(reporter.getUserName() + " has reported you");
        receiver.getNotifications().add(N.getID());
        logger.info(reporter.getUserName() + " reported " + receiver.getUserName());
        DataBase.getDB().Save();
    }

    public void block(User blocker,User receiver){
        if (!DataBase.getDB().doesBlock(blocker.getUserName(), receiver.getUserName()))
            DataBase.getDB().getUser(blocker.getUserName()).getBlackList().add(receiver.getUserName());
        logger.info(blocker.getUserName() + " blocked " + receiver.getUserName());
        DataBase.getDB().Save();
    }

    public void unBlock(User blocker,User receiver){
        for(int i = 0;i < blocker.getBlackList().size();i++){
            if(blocker.getBlackList().get(i).equals(receiver.getUserName())){
                blocker.getBlackList().remove(i);
                break;
            }
        }
        logger.info(blocker.getUserName() + " unblocked " + receiver.getUserName());
        DataBase.getDB().Save();
    }
}
