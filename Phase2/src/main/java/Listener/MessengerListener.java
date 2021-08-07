package Listener;

import Controller.DataBase.DataBase;
import Controller.Pages.Messenger;
import Event.Texts.Message.NewMessageEvent;
import Model.Message;
import Model.Text;
import Model.User;

import java.util.ArrayList;

public class MessengerListener {
    private final Messenger messenger;

    public MessengerListener(){
        messenger = new Messenger();
    }

    public void NewMessage(ArrayList<User> to,NewMessageEvent event){
        for(User user : to) {
            Message message = new Message(DataBase.getDB().getCurrentUser().getUserName(),user.getUserName(),event.getText());
            message.setImage(event.getImage());
            DataBase.getDB().getCurrentUser().addMessage(message);
            user.addMessage(message);
        }
        DataBase.getDB().Save();
        DataBase.getDB().newChatUsers();
    }

    public ArrayList<Text> getChats(){
        return messenger.getChat(DataBase.getDB().getCurrentUser(),DataBase.getDB().getChatUsers().get(0));
    }

    public ArrayList<User> getContacts(){
        return messenger.getContacts();
    }

    public ArrayList<User> getInbox(){
        return messenger.getInbox();
    }

    public int getUnseen(User user){
        return messenger.getUnseen(user);
    }
}
