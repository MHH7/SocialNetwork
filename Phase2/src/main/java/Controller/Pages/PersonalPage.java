package Controller.Pages;

import Controller.DataBase.DataBase;
import Model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class PersonalPage {
    static private final Logger logger = LogManager.getLogger(PersonalPage.class);

    public ArrayList<User> getFollowers(){
        User user = DataBase.getDB().getCurrentUser();
        ArrayList<User> temp = new ArrayList<>();
        for(String s : user.getFollowers()){
            if(DataBase.getDB().getUser(s).isActive()) {
                temp.add(DataBase.getDB().getUser(s));
            }
        }
        return temp;
    }

    public ArrayList<User> getFollowings(){
        User user = DataBase.getDB().getCurrentUser();
        ArrayList<User> temp = new ArrayList<>();
        for (String s : user.getFollowings()) {
            if (DataBase.getDB().getUser(s).isActive()) {
                temp.add(DataBase.getDB().getUser(s));
            }
        }
        return temp;
    }

    public ArrayList<User> getBlackList(){
        User user = DataBase.getDB().getCurrentUser();
        ArrayList<User> temp = new ArrayList<>();
        for (String s : user.getBlackList()) {
            if (DataBase.getDB().getUser(s).isActive()) {
                temp.add(DataBase.getDB().getUser(s));
            }
        }
        return temp;
    }

    public void addGroup(String name){
        for(Group g : DataBase.getDB().getCurrentUser().getGroups())if(g.getName().equals(name))return;
        DataBase.getDB().getCurrentUser().getGroups().add(new Group(name));
        logger.info("Group " + name + " created!");
        DataBase.getDB().Save();
    }

    public void deleteGroup(String name){
        User user = DataBase.getDB().getCurrentUser();;
        int p = 0;
        for(int i = 0;i < user.getGroups().size();i++){
            if(user.getGroups().get(i).getName().equals(name)){
                user.getGroups().remove(i);
                p = 1;
                break;
            }
        }
        if(p == 1)logger.info("Group " + name + " deleted!");
        DataBase.getDB().Save();
    }
}
