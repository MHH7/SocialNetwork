package Model;

import Logic.DataBase;
import Logic.Reader;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Message extends Text{
    private final String sender;
    private final String receiver;

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public Message(String sender, String receiver,String text){
        super(text);
        DataBase.getDB().addText(this);
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public void show() {
        System.out.println("From : " + sender + " to : " + receiver);
        super.show();
        System.out.println(date);
    }

    public void open(User user){
        isRead = true;
        System.out.println("From : " + sender + " to : " + receiver);
        super.show();
        System.out.println(date);
        System.out.println("1_Save");
        System.out.println("2_Return");
        int x = 0;
        while (x < 1 || x > 2){
            x = Reader.get().nextInt();
            if(x == 1){
                user.addSaved(this);
            }
            else if(x == 2){
                return;
            }
            else{
                System.out.println("Invalid Input");
            }
        }
    }
}
