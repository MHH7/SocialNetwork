package Model;

import Logic.DataBase;
import Logic.Reader;

import java.util.Scanner;

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
    public void show() {
        super.show();
    }

    public void open(User user){
        super.show();
        System.out.println("1_Accept");
        System.out.println("2_Reject");
        System.out.println("3_Anonymous Reject");
        System.out.println("4_Return");
        int x = 0;
        while (x < 1 || x > 4){
            x = Reader.get().nextInt();
            if(x == 1){
                isRead = true;
                Notification N = new Notification(_sender + " has started following you");
                receiver.getNotifications().add(N.getID());
                sender.getFollowings().add(_receiver);
                receiver.getFollowers().add(_sender);
            }
            else if(x == 2){
                isRead = true;
                Notification N = new Notification(user.getUserName() + " has rejected your request");
                sender.getNotifications().add(N.getID());
            }
            else if(x == 3){
                isRead = true;
            }
            else if(x == 4){
                return;
            }
            else{
                System.out.println("Invalid Input");
            }
            DataBase.getDB().Save();
        }
    }
}
