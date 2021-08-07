package Controller.Pages;

import Controller.DataBase.DataBase;
import Graphic.Main;
import Model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.ListIterator;

public class Settings {
    static private final Logger logger = LogManager.getLogger(Settings.class);

    public void deleteUser(User user){
        for(User u : DataBase.getDB().getAllUsers()){
            ListIterator<Integer> it = u.getMessages().listIterator();
            while (it.hasNext()){
                Text m = DataBase.getDB().getText(it.next());
                if(((Message)m).getReceiver().equals(user.getUserName()) || ((Message)m).getSender().equals(user.getUserName())){
                    it.remove();
                }
            }
            ListIterator<Integer> it2 = u.getTweets().listIterator();
            while (it2.hasNext()){
                Text t = DataBase.getDB().getText(it2.next());
                delete(user,(Tweet) t);
                if(((Tweet)t).getUser().equals(user.getUserName())){
                    it2.remove();
                }
            }
            ListIterator<Integer> it3 = u.getRequests().listIterator();
            while (it3.hasNext()){
                Text r = DataBase.getDB().getText(it3.next());
                if(((Request)r).get_receiver().equals(user.getUserName()) || ((Request)r).get_sender().equals(user.getUserName())){
                    it3.remove();
                }
            }
            ListIterator<Integer> it4 = u.getSaved().listIterator();
            while (it4.hasNext()){
                Text tt = DataBase.getDB().getText(it4.next());
                if(tt instanceof Tweet){
                    if(((Tweet) tt).getUser().equals(user.getUserName())){
                        it4.remove();
                    }
                }
                else if(tt instanceof Message){
                    if(((Message) tt).getReceiver().equals(user.getUserName()) || ((Message) tt).getSender().equals(user.getUserName())){
                        it4.remove();
                    }
                }
                else if(tt instanceof Request){
                    if(((Request) tt).get_receiver().equals(user.getUserName()) || ((Request) tt).get_sender().equals(user.getUserName())){
                        it4.remove();
                    }
                }
            }
            ListIterator<String> it5 = u.getFollowers().listIterator();
            while (it5.hasNext()){
                String s = it5.next();
                if(s.equals(user.getUserName())){
                    it5.remove();
                }
            }
            it5 = u.getFollowings().listIterator();
            while (it5.hasNext()){
                String s = it5.next();
                if(s.equals(user.getUserName())){
                    it5.remove();
                }
            }
            it5 = u.getBlackList().listIterator();
            while (it5.hasNext()){
                String s = it5.next();
                if(s.equals(user.getUserName())){
                    it5.remove();
                }
            }
            it5 = u.getMutes().listIterator();
            while (it5.hasNext()){
                String s = it5.next();
                if(s.equals(user.getUserName())){
                    it5.remove();
                }
            }
        }
        ListIterator<User> it6 = DataBase.getDB().getAllUsers().listIterator();
        while (it6.hasNext()){
            User uu = it6.next();
            if(uu.equals(user)){
                it6.remove();
            }
        }
        ListIterator<Text> it7 = DataBase.getDB().getAllTexts().listIterator();
        while (it7.hasNext()){
            Text m = it7.next();
            if(m instanceof Message) {
                if(((Message)m).getReceiver() != null) {
                    if (((Message) m).getReceiver().equals(user.getUserName()) || ((Message) m).getSender().equals(user.getUserName())) {
                        it7.remove();
                    }
                }
            }
            else if(m instanceof Tweet){
                if(((Tweet)m).getUser().equals(user.getUserName())){
                    it7.remove();
                }
            }
            else if(m instanceof Request){
                if(((Request)m).get_receiver().equals(user.getUserName()) || ((Request)m).get_sender().equals(user.getUserName())){
                    it7.remove();
                }
            }
        }

        ListIterator<ChatRoom> it8 = DataBase.getDB().getChatRooms().listIterator();
        while (it8.hasNext()){
            ChatRoom chatRoom = it8.next();
            for(int k = 0;k < chatRoom.getUsers().size();k++){
                if(chatRoom.getUsers().get(k).equals(user.getUserName())){
                    chatRoom.getUsers().remove(k);
                    break;
                }
            }
        }

        logger.info(user.getUserName() + " deleted account successfully!");
        DataBase.getDB().Save();
        DataBase.getDB().start();
    }

    public void deleteMessage(Message message){
        int id = message.getID();
        for(User u : DataBase.getDB().getAllUsers()){
            ListIterator<Integer> it = u.getMessages().listIterator();
            while (it.hasNext()){
                Message m = (Message) DataBase.getDB().getText(it.next());
                if(m.getID() == id){
                    it.remove();
                }
            }
            it = u.getSaved().listIterator();
            while (it.hasNext()){
                Text m = DataBase.getDB().getText(it.next());
                if(m instanceof Message){
                    if(m.getID() == id){
                        it.remove();
                    }
                }
            }
        }
        for(ChatRoom c : DataBase.getDB().getChatRooms()){
            ListIterator<Integer> it = c.getMessages().listIterator();
            while (it.hasNext()){
                Text m = DataBase.getDB().getText(it.next());
                if(m instanceof Message){
                    if(m.getID() == id){
                        it.remove();
                    }
                }
            }
        }
        logger.info("Message " + id + " deleted!");
        DataBase.getDB().Save();
    }

    public void deleteTweet(Tweet tweet){
        int id = tweet.getID();
        delete(DataBase.getDB().getUser(tweet.getUser()),tweet);
        for(User u : DataBase.getDB().getAllUsers()){
            ListIterator<Integer> it = u.getTweets().listIterator();
            while (it.hasNext()){
                Tweet tw = (Tweet) DataBase.getDB().getText(it.next());
                if(tw.getID() == id){
                    it.remove();
                }
            }
            it = u.getSaved().listIterator();
            while (it.hasNext()){
                Text tw = DataBase.getDB().getText(it.next());
                if(tw instanceof Tweet){
                    if(tw.getID() == id){
                        it.remove();
                    }
                }
            }
        }
        logger.info("Tweet " + id + " deleted!");
        DataBase.getDB().Save();
    }

    public void delete(User user,Tweet t){
        ListIterator<Integer> it = t.getComments().listIterator();
        while (it.hasNext()){
            Text tw = DataBase.getDB().getText(it.next());
            delete(user,(Tweet) tw);
        }
        it = t.getComments().listIterator();
        while (it.hasNext()){
            Text tw = DataBase.getDB().getText(it.next());
            if(((Tweet)tw).getUser().equals(user.getUserName())){
                it.remove();
            }
        }
        DataBase.getDB().Save();
    }

    public void logOut(User user){
        user.setOnlie(false);
        user.setLastSeen(new Date());
        logger.info(user.getUserName() + " logged out of the account!");
        DataBase.getDB().start();
    }
}
