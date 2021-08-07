package Controller.Pages;

import Controller.DataBase.DataBase;
import Model.*;

import java.util.ArrayList;

public class Messenger {

    public ArrayList<User> getContacts(){
        User user = DataBase.getDB().getCurrentUser();
        ArrayList<User> contacts = new ArrayList<>();
        for (User u : DataBase.getDB().getAllUsers()) {
            if ((DataBase.getDB().doesFollow(user.getUserName(),u.getUserName()) || DataBase.getDB().doesFollow(u.getUserName(), user.getUserName()))
                    && !DataBase.getDB().doesBlock(u.getUserName(),user.getUserName()) && !DataBase.getDB().doesBlock(user.getUserName(),u.getUserName())) {
                contacts.add(u);
            }
        }
        return contacts;
    }

    public ArrayList<Text> getChat(User first,User second){
        if(first.getUserName().equals(second.getUserName()))return getSaved();
        ArrayList<Text> all = new ArrayList<>();
        for(int i = 0;i < first.getMessages().size();i++){
            Text message = DataBase.getDB().getText(first.getMessages().get(i));
            if(message instanceof Message) {
                if ((((Message)message).getReceiver().equals(first.getUserName()) && ((Message)message).getSender().equals(second.getUserName())) || (((Message)message).getSender().equals(first.getUserName()) && ((Message)message).getReceiver().equals(second.getUserName()))) {
                    all.add((message));
                }
            }
            else if(message instanceof Tweet){
                if(((Tweet) message).getForward().equals(second.getUserName())){
                    all.add(message);
                }
            }
        }
        return all;
    }

    public ArrayList<Text> getSaved(){
        ArrayList<Text> all = new ArrayList<>();
        ArrayList<Integer> saved = DataBase.getDB().getCurrentUser().getSaved();
        for(int i = 0;i < saved.size();i++){
            all.add(DataBase.getDB().getText(saved.get(i)));
        }
        return all;
    }

    public ArrayList<User> getInbox(){
        User user = DataBase.getDB().getCurrentUser();
        ArrayList<User> unseen = new ArrayList<>();
        ArrayList<User> seen = new ArrayList<>();
        for(User u : DataBase.getDB().getAllUsers()){
            if(u.getUserName().equals(user.getUserName()))continue;
            int p = 0;
            for(User u2 : unseen){
                if(u2.getUserName().equals(u.getUserName())){
                    p = 1;
                    break;
                }
            }
            if(p == 1)continue;
            for(int m : user.getMessages()){
                if(((Message)DataBase.getDB().getText(m)).getSender().equals(u.getUserName()) && ! DataBase.getDB().getText(m).isRead() &&
                        !((Message)DataBase.getDB().getText(m)).getSender().equals(user.getUserName())){
                    p = 1;
                }
            }
            if(p == 1)unseen.add(u);
        }
        for(User u : DataBase.getDB().getAllUsers()){
            int p = 0;
            for(int m : user.getMessages()){
                if(((Message)DataBase.getDB().getText(m)).getSender().equals(u.getUserName()) || ((Message)DataBase.getDB().getText(m)).getReceiver().equals(u.getUserName())){
                    p = 1;
                }
            }
            for(User u2 : unseen){
                if(u2.getUserName().equals(u.getUserName()))p = 2;
            }
            if(p == 2)continue;
            if(p == 1)seen.add(u);
        }
        ArrayList<User> allChats = new ArrayList<>();
        for (User u : unseen) {
            allChats.add(u);
        }
        for (User u : seen) {
            allChats.add(u);
        }
        return allChats;
    }

    public int getUnseen(User u){
        User user = DataBase.getDB().getCurrentUser();
        int cnt = 0;
        ArrayList<Text> Messages = getChat(DataBase.getDB().getCurrentUser(), u);
        for (int j = 0;j < Messages.size();j++) {
            Text m = Messages.get(j);
            if(m instanceof Message) {
                if ((((Message) m).getReceiver().equals(u.getUserName()) || ((Message) m).getSender().equals(u.getUserName())) && !m.isRead()
                && !((Message)m).getSender().equals(user.getUserName()))
                    cnt++;
            }
            else if(m instanceof Tweet){
                if(((Tweet)m).getForward().equals(u.getUserName()))cnt++;
            }
        }
        return cnt;
    }
}
