package Model;

import Controller.DataBase.DataBase;
import Controller.Pages.Login;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ChatRoom {
    private final String name;
    private final ArrayList<String> users;
    private final ArrayList<Integer> messages;
    static private final Logger logger = LogManager.getLogger(ChatRoom.class);

    public ChatRoom(String name){
        this.name = name;
        users = new ArrayList<>();
        messages = new ArrayList<>();
        DataBase.getDB().Save();
    }

    public void addUser(User user){
        for(String u : users)if(u.equals(user.getUserName()))return;
        users.add(user.getUserName());
        logger.info(user.getUserName() + " has added to " + name + " Chat Room!");
        DataBase.getDB().Save();
    }

    public ArrayList<String> getUsers() {
        DataBase.getDB().Save();
        return users;
    }

    public void removeUser(String user){
        for(int i = 0;i < users.size();i++){
            if(users.get(i).equals(user)){
                users.remove(i);
                break;
            }
        }
        logger.info(user + " has removed from " + name + " Chat Room!");
        DataBase.getDB().Save();
    }

    public void addMessage(int MessageID){
        messages.add(MessageID);
        logger.info("New Message in " + name + " Chat Room!");
        DataBase.getDB().Save();
    }

    public ArrayList<Integer> getMessages() {
        return messages;
    }

    public String getName() {
        return name;
    }
}
