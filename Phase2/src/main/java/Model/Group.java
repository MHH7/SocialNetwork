package Model;

import Controller.DataBase.DataBase;
import Controller.Pages.Login;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Group {
    private final String name;
    private final ArrayList<String> users;
    static private final Logger logger = LogManager.getLogger(Group.class);

    public Group(String name){
        users = new ArrayList<>();
        this.name = name;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public String getName() {
        return name;
    }

    public void addMember(String newMember){
        for(String user : users)if(user.equals(newMember))return;
        User user = DataBase.getDB().getCurrentUser();
        for(User u : DataBase.getDB().getAllUsers()) {
            if (u.getUserName().equals(newMember)) {
                if (DataBase.getDB().doesFollow(user.getUserName(), newMember)) {
                    users.add(newMember);
                    break;
                }
            }
        }
        logger.info(newMember + " added to " + name + " Group!");
        DataBase.getDB().Save();
    }

    public void removeMember(String removeMember){
        for(int i = 0;i < users.size();i++){
            if(users.get(i).equals(removeMember)){
                users.remove(i);
                break;
            }
        }
        logger.info(removeMember + " removed from " + name + " Group!");
        DataBase.getDB().Save();
    }
}
