package Logic.Pages;

import Logic.DataBase;
import Logic.Reader;
import Model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

public class Messenger {

    public void start(User user){
        while (true) {
            System.out.println("----------Messenger----------");
            System.out.println("1_New Conversation");
            System.out.println("2_Saved Messages");
            System.out.println("3_Inbox");
            System.out.println("4_Group Message");
            System.out.println("5_Return");
            int x = 0;
            while (x < 1 || x > 5) {
                x = Reader.get().nextInt();
                if (x == 1) {
                    newConversation(user);
                } else if (x == 2) {
                    savedMessages(user);
                } else if (x == 3) {
                    inbox(user);
                } else if(x == 4){
                    groupMessage(user);
                } else if(x == 5){
                    return;
                }
                else{
                    System.out.println("Invalid Input!");
                }
                DataBase.getDB().Save();
            }
        }
    }

    public void groupMessage(User user){
        while (true){
            System.out.println("1_Choose from followers");
            System.out.println("2_Choose from groups");
            System.out.println("3_Send to all");
            System.out.println("4_Return");
            int x = 0;
            while (x < 1 || x > 4){
                x = Reader.get().nextInt();
                if(x == 1){
                    ArrayList<User> selected = new ArrayList<>();
                    String s = "";
                    while (true){
                        System.out.println("0_Return");
                        System.out.print("Enter user name : ");
                        s = Reader.get().nextLine();
                        if(s.equals("0")){
                            break;
                        }
                        else{
                            if(DataBase.getDB().doesFollow(user.getUserName(),s)){
                                selected.add(DataBase.getDB().getUser(s));
                            }
                            else{
                                System.out.println("This user does not follow " + s);
                            }
                        }
                    }
                    s = Reader.get().nextLine();
                    for(User u : selected){
                        Message m = new Message(user.getUserName(),u.getUserName(),s);
                        user.addMessage(m);
                        u.addMessage(m);
                    }
                }
                else if(x == 2){
                    ArrayList<Group> selected = new ArrayList<>();
                    String s = "";
                    while (true){
                        System.out.println("0_Return");
                        System.out.print("Enter group name : ");
                        s = Reader.get().nextLine();
                        if(s.equals("0")){
                            break;
                        }
                        else{
                            if(user.groupExists(s)){
                                selected.add(user.getGroup(s));
                            }
                            else{
                                System.out.println("This group does not exists!");
                            }
                        }
                    }
                    s = Reader.get().nextLine();
                    for(Group g : selected){
                        for(String S : g.getUsers()){
                            Message m = new Message(user.getUserName(),S,s);
                            user.addMessage(m);
                            DataBase.getDB().getUser(S).addMessage(m);
                        }
                    }
                }
                else if(x == 3){
                    String S = Reader.get().nextLine();
                    for(String s : user.getFollowers()){
                        Message m = new Message(user.getUserName(),DataBase.getDB().getUser(s).getUserName(),S);
                        DataBase.getDB().getUser(s).getMessages().add(m.getID());
                        user.addMessage(m);
                    }
                }
                else if(x == 4){
                    return;
                }
                else{
                    System.out.println("Invalid Input!");
                }
                DataBase.getDB().Save();
            }
        }
    }

    public void newConversation(User user){
        while(true) {
            ArrayList<User> contacts = new ArrayList<>();
            System.out.println("Select user : ");
            int i = 1;
            for (User u : DataBase.getDB().getAllUsers()) {
                if ((DataBase.getDB().doesFollow(user.getUserName(),u.getUserName()) || DataBase.getDB().doesFollow(u.getUserName(), user.getUserName()))
                        && !DataBase.getDB().doesBlock(u.getUserName(),user.getUserName()) && !DataBase.getDB().doesBlock(user.getUserName(),u.getUserName())) {
                    System.out.print(i + "_");
                    DataBase.getDB().getProfile().show(u);
                    contacts.add(u);
                    i++;
                }
            }
            i--;
            System.out.println((i + 1) + "_Return");
            int x = 0;
            while (x < 1 || x > contacts.size()) {
                x = Reader.get().nextInt();
                if (x >= 1 && x <= contacts.size()) {
                    showChat(user, contacts.get(x - 1));
                } else if (x == i + 1) {
                    return;
                } else {
                    System.out.println("Invalid Input");
                }
                DataBase.getDB().Save();
            }
        }
    }

    public void savedMessages(User user){
        for(int i = 0;i < user.getMessages().size();i++){
            Text text = DataBase.getDB().getText(user.getMessages().get(i));
            text.show();
        }
        while(true) {
            int x = 0;
            System.out.println("1_New Message");
            System.out.println("2_Return");
            while (x != 1 && x != 2) {
                x = Reader.get().nextInt();
                if (x == 1) {
                    Message M = new Message(user.getUserName(), user.getUserName(), Reader.get().nextLine());
                    user.addMessage(M);
                    user.addSaved(M);
                } else if (x == 2) {
                    return;
                } else {
                    System.out.println("Invalid Input");
                }
                DataBase.getDB().Save();
            }
        }
    }

    public void inbox(User user){
        while(true) {
            ArrayList<User> unseen = new ArrayList<>();
            ArrayList<User> seen = new ArrayList<>();
            for(User u : DataBase.getDB().getAllUsers()){
                int p = 0;
                for(int m : user.getMessages()){
                    if(DataBase.getDB().getText(m) instanceof Message) {
                        if (((Message) DataBase.getDB().getText(m)).getSender().equals(u.getUserName()) && !((Message) DataBase.getDB().getText(m)).isRead()) {
                            p = 1;
                        }
                    }
                }
                if(p == 1)unseen.add(u);
            }
            for(User u : DataBase.getDB().getAllUsers()){
                int p = 0;
                for(User u2 : unseen){
                    if(u.getUserName().equals(u2.getUserName())){
                        p = 1;
                    }
                }
                if(p == 1)continue;
                for(int m : user.getMessages()){
                    if(DataBase.getDB().getText(m) instanceof Message) {
                        if (((Message) DataBase.getDB().getText(m)).getSender().equals(u.getUserName()) && !((Message) DataBase.getDB().getText(m)).isRead()) {
                            p = 1;
                        }
                    }
                }
                if(p == 1)seen.add(u);
            }
            System.out.println("0_Return");
            int i = 1;
            ArrayList<User> allChats = new ArrayList<>();
            for (User u : seen) {
                System.out.print(i + "_");
                DataBase.getDB().getProfile().show(u);
                allChats.add(u);
                i++;
            }
            for (User u : unseen) {
                System.out.print(i + "_");
                DataBase.getDB().getProfile().show(u);
                allChats.add(u);
                int cnt = 0;
                for (int j = 0;j < user.getMessages().size();j++) {
                    Text m = DataBase.getDB().getText(user.getMessages().get(j));
                    if(m instanceof Message) {
                        if ((((Message)m).getReceiver().equals(u.getUserName()) || ((Message)m).getSender().equals(u.getUserName())) && !m.isRead())
                            cnt++;
                    }
                    else if(m instanceof Tweet){
                        if(((Tweet)m).getForward().equals(u.getUserName()))cnt++;
                    }
                }
                System.out.println("Unread messages : " + cnt);
                i++;
            }
            i--;
            int x = -1;
            while (x < 0 || x > i) {
                x = Reader.get().nextInt();
                if (x == 0) {
                    return;
                } else if (x >= 1 && x <= i) {
                    showChat(user, allChats.get(x - 1));
                } else {
                    System.out.println("Invalid Input");
                }
                DataBase.getDB().Save();
            }
        }
    }

    public void showChat(User first,User second){
        ArrayList<Text> all = new ArrayList<>();
        System.out.println("0_Return");
        System.out.println("1_New Message");
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
        int i = 2;
        for(Text message : all){
            System.out.print(i + "_");
            message.show();
            i++;
        }
        i--;
        int x = -1;
        while (x < 0 || x > i){
            x = Reader.get().nextInt();
            if(x == 0){
                return;
            }
            else if(x == 1){
                Message m = new Message(first.getUserName(),second.getUserName(),Reader.get().nextLine());
                first.addMessage(m);
                second.addMessage(m);
            }
            else if(x >= 2 && x <= i){
                all.get(x - 2).open(first);
            }
            else{
                System.out.println("Invalid Input");
            }
            DataBase.getDB().Save();
        }
    }
}
