package Listener;

import Controller.DataBase.DataBase;
import Event.Texts.Message.NewMessageEvent;
import Model.ChatRoom;
import Model.Message;

public class ChatRoomListener {
    private final ChatRoom chatRoom;

    public ChatRoomListener(){
        chatRoom = DataBase.getDB().getCurrentChatRoom();
    }

    public void addUser(String user){
        if(DataBase.getDB().getUser(user) != null)chatRoom.addUser(DataBase.getDB().getUser(user));
    }

    public void leaveGroup(String user){
        chatRoom.removeUser(user);
    }

    public void addMessage(NewMessageEvent event){
        Message message = new Message(DataBase.getDB().getCurrentUser().getUserName(),event.getText());
        chatRoom.addMessage(message.getID());
        message.setChatRoomName(chatRoom.getName());
        DataBase.getDB().Save();
    }

    public void newGroup(String groupName){
        ChatRoom chatRoom = new ChatRoom(groupName);
        DataBase.getDB().addChatRoom(chatRoom);
        chatRoom.addUser(DataBase.getDB().getCurrentUser());
        DataBase.getDB().Save();
    }
}
