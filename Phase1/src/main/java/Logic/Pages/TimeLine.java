package Logic.Pages;

import Logic.DataBase;
import Logic.Reader;
import Model.Text;
import Model.Tweet;
import Model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TimeLine {

    public void start(User user){
        System.out.println("----------Time Line----------");
        ArrayList<Integer> tweets = new ArrayList<>();
        for(String s :user.getFollowings()){
            for(int i = 0;i < DataBase.getDB().getUser(s).getTweets().size();i++){
                Text tweet = DataBase.getDB().getText(DataBase.getDB().getUser(s).getTweets().get(i));
                if(DataBase.getDB().getUser(((Tweet)tweet).getUser()).isActive())tweets.add(tweet.getID());
            }
        }
        Collections.shuffle(tweets);
        if(tweets.size() == 0){
            System.out.println("No tweets to show here!");
            return;
        }
        DataBase.getDB().getText(tweets.get(0)).show();
        while (true){
            int i = 0;
            System.out.println("0_Return");
            System.out.println("1_Open");
            System.out.println("2_Next");
            System.out.println("3_Previous");
            int x = -1;
            while (x < 0 || x > 3){
                x = Reader.get().nextInt();
                if(x == 0){
                    return;
                }
                else if(x == 1){
                    DataBase.getDB().getText(tweets.get(i)).open(user);
                }
                else if(x == 2){
                    i++;
                    if(i == tweets.size())i = 0;
                    DataBase.getDB().getText(tweets.get(i)).show();
                }
                else if(x == 3){
                    i--;
                    if(i == -1)i = tweets.size() - 1;
                    DataBase.getDB().getText(tweets.get(i)).show();
                }
                else{
                    System.out.println("Invalid Input");
                }
            }
        }
    }
}
