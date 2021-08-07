package Logic.Pages;

import Logic.DataBase;
import Logic.Reader;
import Model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class PersonalPage {

    public void start(User user){
        while (true){
            System.out.println("----------Personal Page----------");
            System.out.println("1_New Tweet");
            System.out.println("2_Old Tweets");
            System.out.println("3_Change Info");
            System.out.println("4_Lists");
            System.out.println("5_Show Info");
            System.out.println("6_Notifications");
            System.out.println("7_Requests");
            System.out.println("8_Return");
            int x = 0;
            while (x < 1 || x > 8){
                x = Reader.get().nextInt();
                if(x == 1){
                    System.out.println("Enter tweet : ");
                    Tweet tweet = new Tweet(Reader.get().nextLine(),user.getUserName());
                    user.addTweet(tweet);
                }
                else if(x == 2){
                    showTweets(user);
                }
                else if(x == 3){
                    DataBase.getDB().getLogin().getMainPage().getSettings().privacySettings(user);
                }
                else if(x == 4){
                    showLists(user);
                }
                else if(x == 5){
                    DataBase.getDB().getProfile().open(user,user);
                }
                else if(x == 6){
                    showNotifications(user);
                }
                else if(x == 7){
                    showRequests(user);
                }
                else if(x == 8){
                    return;
                }
                else{
                    System.out.println("Invalid Input");
                }
                DataBase.getDB().Save();
            }
        }
    }

    public void showLists(User user){
        while (true){
            System.out.println("----------Lists----------");
            System.out.println("1_Followers");
            System.out.println("2_Followings");
            System.out.println("3_Blacklist");
            System.out.println("4_Return");
            int x = 0;
            while (x < 1 || x > 4){
                x = Reader.get().nextInt();
                if(x == 1){
                    Followers(user);
                }
                else if(x == 2){
                    showFollowings(user);
                }
                else if(x == 3){
                    showBlackList(user);
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

    public void showRequests(User user){
        while (true){
            System.out.println("----------Requests----------");
            System.out.println("0_Return");
            for(int j = 0;j < user.getRequests().size();j++){
                if(DataBase.getDB().getText(user.getRequests().get(j)).isRead()){
                    user.getRequests().remove(j);
                    j--;
                }
            }
            int i = 1;
            for(int j = 0;j < user.getRequests().size();j++){
                Text r = DataBase.getDB().getText(user.getRequests().get(j));
                System.out.print(i + "_");
                r.show();
                i++;
            }
            i--;
            int x = -1;
            while (x < 0 || x > i){
                x = Reader.get().nextInt();
                if(x == 0){
                    return;
                }
                else if(x >= 1 && x <= i){
                    if(DataBase.getDB().getText(user.getRequests().get(x - 1)) instanceof Request)
                    System.out.println(1);
                    DataBase.getDB().getText(user.getRequests().get(x - 1)).open(user);
                }
                else{
                    System.out.println("Invalid Input!");
                }
            }
            DataBase.getDB().Save();
        }
    }

    public void showNotifications(User user){
        System.out.println("----------Notifications----------");
        System.out.println("0_Return");
        for(int i = 0;i < user.getNotifications().size();i++){
            Text n = DataBase.getDB().getText(user.getNotifications().get(i));
            n.show();
        }
        int x = -1;
        while (x != 0){
            x = Reader.get().nextInt();
            if(x == 0)return;
            else System.out.println("Invalid Input!");
        }
    }

    public void showTweets(User user){
        while (true) {
            System.out.println("0_Return");
            int i = 1;
            for (int j = 0;j < user.getTweets().size();j++) {
                Text tweet = DataBase.getDB().getText(user.getTweets().get(j));
                System.out.print(i + "_");
                tweet.show();
                i++;
            }
            i--;
            int x = -1;
            while (x < 0 || x > i){
                x = Reader.get().nextInt();
                if(x == 0){
                    return;
                }
                else if(x >= 1 && x <= i){
                    DataBase.getDB().getText(user.getTweets().get(x - 1)).open(user);
                }
                else{
                    System.out.println("Invalid Input");
                }
                DataBase.getDB().Save();
            }
        }
    }

    public void Followers(User user){
        while (true) {
            System.out.println("1_Show Groups");
            System.out.println("2_Show Followers");
            System.out.println("3_Return");
            int x = 0;
            while (x < 1 || x > 3){
                x = Reader.get().nextInt();
                if(x == 1){
                    showGroups(user);
                }
                else if(x == 2){
                    showFollowers(user);
                }
                else if(x == 3){
                    return;
                }
                else{
                    System.out.println("Invalid Input");
                }
                DataBase.getDB().Save();
            }
        }
    }

    public void showGroups(User user){
        while(true){
            System.out.println("----------Groups----------");
            System.out.println("0_Return");
            System.out.println("1_New Group");
            System.out.println("2_Add New Member");
            System.out.println("3_Remove Member");
            System.out.println("4_Delete Group");
            int x = 0;
            while (x < 1 || x > 4){
                x = Reader.get().nextInt();
                if(x == 0){
                    return;
                }
                else if(x == 1){
                    System.out.print("Enter Group Name : ");
                    user.getGroups().add(new Group(Reader.get().nextLine()));
                }
                else if(x == 2){
                    String s = "";
                    while (true){
                        System.out.print("Enter Group Name : ");
                        s = Reader.get().nextLine();
                        int p = 0;
                        Group group = new Group("");
                        for(Group g : user.getGroups()){
                            if(g.getName().equals(s)){
                                p = 1;
                                group = g;
                            }
                        }
                        if(p == 1){
                            group.addMember(user);
                            break;
                        }
                        else{
                            System.out.println("This group doesn't exist!");
                        }
                    }
                }
                else if(x == 3){
                    String s = "";
                    while (true){
                        System.out.print("Enter Group Name : ");
                        s = Reader.get().nextLine();
                        int p = 0;
                        Group group = new Group("");
                        for(Group g : user.getGroups()){
                            if(g.getName().equals(s)){
                                p = 1;
                                group = g;
                            }
                        }
                        if(p == 1){
                            group.removeMember();
                            break;
                        }
                        else{
                            System.out.println("This group doesn't exist!");
                        }
                    }
                }
                else if(x == 4){
                    String s = "";
                    while (true){
                        System.out.print("Enter Group Name : ");
                        s = Reader.get().nextLine();
                        int p = 0;
                        Group group = new Group("");
                        for(Group g : user.getGroups()){
                            if(g.getName().equals(s)){
                                p = 1;
                                group = g;
                            }
                        }
                        if(p == 1){
                            for(int i = 0;i < user.getGroups().size();i++){
                                if(user.getGroups().get(i).getName().equals(group.getName())){
                                    user.getGroups().remove(i);
                                    break;
                                }
                            }
                            break;
                        }
                        else{
                            System.out.println("This group doesn't exist!");
                        }
                    }
                }
                else{
                    System.out.println("Invalid Input!");
                }
            }
            DataBase.getDB().Save();
        }
    }

    public void showFollowers(User user){
        while (true){
            System.out.println("----------Followers----------");
            System.out.println("0_Return");
            ArrayList<User> temp = new ArrayList<>();
            int i = 1;
            for(String s : user.getFollowers()){
                if(DataBase.getDB().getUser(s).isActive()) {
                    System.out.print(i + "_");
                    DataBase.getDB().getProfile().show(DataBase.getDB().getUser(s));
                    temp.add(DataBase.getDB().getUser(s));
                    i++;
                }
            }
            i--;
            int x = -1;
            while (x < 0 || x > i){
                x = Reader.get().nextInt();
                if(x == 0){
                    return;
                }
                else if(x >= 1 && x <= i){
                    DataBase.getDB().getProfile().open(temp.get(x - 1),user);
                }
                else{
                    System.out.println("Invalid Input");
                }
                DataBase.getDB().Save();
            }
        }
    }

    public  void showFollowings(User user){
        while (true){
            System.out.println("----------Followings----------");
            System.out.println("0_Return");
            ArrayList<User> temp = new ArrayList<>();
            int i = 1;
            for(String s : user.getFollowings()){
                if(DataBase.getDB().getUser(s).isActive()) {
                    System.out.print(i + "_");
                    DataBase.getDB().getProfile().show(DataBase.getDB().getUser(s));
                    temp.add(DataBase.getDB().getUser(s));
                    i++;
                }
            }
            i--;
            int x = -1;
            while (x < 0 || x > i){
                x = Reader.get().nextInt();
                if(x == 0){
                    return;
                }
                else if(x >= 1 && x <= i){
                    DataBase.getDB().getProfile().open(temp.get(x - 1),user);
                }
                else{
                    System.out.println("Invalid Input");
                }
                DataBase.getDB().Save();
            }
        }
    }

    public void showBlackList(User user){
        while (true){
            System.out.println("----------Black List----------");
            System.out.println("0_Return");
            int i = 1;
            ArrayList<User> temp = new ArrayList<>();
            for(String s : user.getBlackList()){
                if(DataBase.getDB().getUser(s).isActive()) {
                    System.out.print(i + "_");
                    temp.add(DataBase.getDB().getUser(s));
                    DataBase.getDB().getProfile().show(DataBase.getDB().getUser(s));
                    i++;
                }
            }
            i--;
            int x = -1;
            System.out.println("Enter a number between 1 to " + i + " to remove from blacklist");
            while (x < 0 || x > i){
                x = Reader.get().nextInt();
                if(x == 0){
                    return;
                }
                else if(x >= 1 && x <= i){
                    temp.remove(x - 1);
                }
                else{
                    System.out.println("Invalid Input");
                }
                DataBase.getDB().Save();
            }
        }
    }
}
