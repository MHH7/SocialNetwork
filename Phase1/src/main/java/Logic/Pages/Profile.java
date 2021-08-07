package Logic.Pages;

import Logic.DataBase;
import Logic.Reader;
import Model.*;

import java.util.Scanner;

public class Profile{

    public void show(User user){
        System.out.println(user.getUserName());
    }

    public void open(User user,User observer){
        System.out.println("Name : " + user.getName());
        System.out.println("Username : " + user.getUserName());
        if(user.getEmailPT().equals(PT.Public)) System.out.println("Email : " + user.getEmail());
        if(user.getPhoneNumberPT().equals(PT.Public)) System.out.println("Phone Number : " + user.getPhoneNumber());
        if(user.getBirthDatePT().equals(PT.Public)) System.out.println("Birthdate : " + user.getBirthDate());
        System.out.println("Bio : " + user.getBio());
        if(user.getLastSeenType().equals("All")) System.out.println("Last seen : "  + user.getLastSeen());
        else if(user.getLastSeenType().equals("Contacts")){
            if(DataBase.getDB().doesFollow(observer.getUserName(),user.getUserName())){
                if(user.isOnlie()) System.out.println("Online");
                else System.out.println("Last seen : " + user.getLastSeen());
            }
            else System.out.println("Last seen recently");
        }
        else System.out.println("Last seen recently");
        if(!user.equals(observer))
        if(DataBase.getDB().doesFollow(observer.getUserName(),user.getUserName())) System.out.println("Followed");
        else System.out.println("Not Followed");
        if(!user.equals(observer)) {
            while (true) {
                if (!DataBase.getDB().doesFollow(observer.getUserName(), user.getUserName()) && !DataBase.getDB().doesBlock(observer.getUserName(), user.getUserName()))
                    System.out.println("0_Follow");
                System.out.println("1_Block");
                System.out.println("2_Report");
                System.out.println("3_Return");
                if (DataBase.getDB().doesFollow(user.getUserName(), observer.getUserName()) || DataBase.getDB().doesFollow(observer.getUserName(), user.getUserName())) {
                    System.out.println("4_New Message");
                }
                int x = -1;
                while (x < 0 || x > 4) {
                    x = Reader.get().nextInt();
                    if (x == 0 && !DataBase.getDB().doesFollow(observer.getUserName(), user.getUserName()) && !DataBase.getDB().doesBlock(observer.getUserName(), user.getUserName())) {
                        Request r = new Request(observer, user);
                        user.getRequests().add(r.getID());
                    } else if (x == 1) {
                        if (!DataBase.getDB().doesBlock(observer.getUserName(), user.getUserName()))
                            DataBase.getDB().getUser(observer.getUserName()).getBlackList().add(user.getUserName());
                    } else if (x == 2) {
                        Notification N = new Notification(observer.getUserName() + " has reported you");
                        user.getNotifications().add(N.getID());
                    } else if (x == 3) {
                        return;
                    } else if (x == 4) {
                        DataBase.getDB().getLogin().getMainPage().getMessenger().showChat(observer, user);
                    } else {
                        x = -1;
                        System.out.println("Invalid Input");
                    }
                    DataBase.getDB().Save();
                }
            }
        }
    }
}
