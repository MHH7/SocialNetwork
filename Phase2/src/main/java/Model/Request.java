package Model;

import Controller.DataBase.DataBase;

public class Request extends Text{
    transient User sender;
    String _sender;
    transient User receiver;
    String _receiver;

    public Request(User sender,User receiver){
        super(sender.getUserName() + " wants to follow you : ");
        DataBase.getDB().addText(this);
        this.sender = sender;
        _sender = sender.getUserName();
        this.receiver = receiver;
        _receiver = receiver.getUserName();
    }

    public String get_receiver() {
        return _receiver;
    }

    public String get_sender() {
        return _sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    @Override
    public String show() {
        return super.show();
    }

    public void accept(){
        isRead = true;
        Notification N = new Notification(_sender + " has started following you");
        receiver.getNotifications().add(N.getID());
        sender.getFollowings().add(_receiver);
        receiver.getFollowers().add(_sender);
    }

    public void reject(){
        isRead = true;
        Notification N = new Notification(receiver.getUserName() + " has rejected your request");
        sender.getNotifications().add(N.getID());
    }

    public void anonymousReject(){
        isRead = true;
    }
}
