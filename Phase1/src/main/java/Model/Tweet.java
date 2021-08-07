package Model;

import Logic.DataBase;
import Logic.Reader;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Tweet extends Text implements Cloneable{
    private final ArrayList<Integer> comments;
    private final String user;
    private int likes;
    private String retweet;
    private String forward;

    public void addComment(Tweet tweet){
        comments.add(tweet.getID());
    }

    public String getUser() {
        return user;
    }

    public ArrayList<Integer> getComments() {
        return comments;
    }

    public void addLike(User user){
        likes++;
    }

    public String getRetweet() {
        return retweet;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public void setRetweet(String retweet) {
        this.retweet = retweet;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public Tweet(String text, String user){
        super(text);
        DataBase.getDB().addText(this);
        comments = new ArrayList<>();
        likes = 0;
        this.user = user;
        forward = "";
        retweet = "";
    }

    @Override
    public void show() {
        if(!getForward().equals("")) System.out.println("Forwarded from : " + getForward());
        if(!getRetweet().equals("")) System.out.println("Retweeted by : " + getRetweet());
        Date d = new Date();
        System.out.println(user + " " + user + " " + d);
        super.show();
    }

    public void open(User user){
        while (true) {
            if (!getForward().equals("")) System.out.println("Forwarded from : " + getForward());
            if (!getRetweet().equals("")) System.out.println("Retweeted by : " + getRetweet());
            isRead = true;
            System.out.println(user.getUserName() + " " + user.getID() + " " + date);
            super.show();
            System.out.println(likes + " Likes");
            System.out.println("1_Show Comments");
            System.out.println("2_Add Comment");
            System.out.println("3_Like");
            System.out.println("4_Retweet");
            System.out.println("5_Save");
            System.out.println("6_Forward");
            System.out.println("7_Return");
            int x = 0;
            while (x < 1 || x > 7) {
                x = Reader.get().nextInt();
                if (x == 1) {
                    showComments(user);
                } else if (x == 2) {
                    this.addComment(new Tweet(Reader.get().nextLine(), user.getUserName()));
                } else if (x == 3) {
                    this.addLike(user);
                } else if (x == 4) {
                    Tweet t = this.clone();
                    t.setRetweet(this.getUser());
                    t.setID(DataBase.getDB().getTextID());
                    user.getTweets().add(t.getID());
                    DataBase.getDB().addText(t);
                } else if (x == 5) {
                    user.addSaved(this);
                } else if (x == 6) {
                    Tweet t = this.clone();
                    t.setForward(this.getUser());
                    t.setID(DataBase.getDB().getTextID());
                    DataBase.getDB().addText(t);
                    System.out.println("Enter username : ");
                    String s = "";
                    while (DataBase.getDB().getUser(s) == null) {
                        s = Reader.get().next();
                        if (DataBase.getDB().getUser(s) != null) {
                            DataBase.getDB().getUser(s).getMessages().add(t.getID());
                        } else {
                            System.out.println("Invalid username!");
                        }
                    }
                } else if (x == 7) {
                    return;
                } else {
                    System.out.println("Invalid Input!");
                }
                DataBase.getDB().Save();
            }
        }
    }

    public void showComments(User user){
        while(true){
            System.out.println("0_Return");
            for(int i = 1;i <= comments.size();i++){
                System.out.print(i + "_");
                DataBase.getDB().getText(comments.get(i - 1)).show();
            }
            int x = -1;
            while (x < 0 || x > user.getTweets().size()){
                x = Reader.get().nextInt();
                if(x == 0){
                    return;
                }
                else if(x >= 1 && x <= comments.size()){
                    DataBase.getDB().getText(comments.get(x - 1)).open(user);
                }
                else{
                    System.out.println("Invalid Input!");
                }
            }
        }
    }

    @Override
    public Tweet clone(){
        try {
            return (Tweet) super.clone();
        } catch (CloneNotSupportedException e){
            e.printStackTrace();
            return null;
        }
    }
}
