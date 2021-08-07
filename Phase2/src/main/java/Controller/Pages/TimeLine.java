package Controller.Pages;

import Controller.DataBase.DataBase;
import Model.Text;
import Model.Tweet;

import java.util.ArrayList;
import java.util.Collections;

public class TimeLine {

    public ArrayList<Integer> getList(){
        ArrayList<Integer> tweets = new ArrayList<>();
        for(String s : DataBase.getDB().getCurrentUser().getFollowings()){
            for(int i = 0;i < DataBase.getDB().getUser(s).getTweets().size();i++){
                Text tweet = DataBase.getDB().getText(DataBase.getDB().getUser(s).getTweets().get(i));
                if(DataBase.getDB().getUser(((Tweet)tweet).getUser()).isActive())tweets.add(tweet.getID());
            }
        }
        Collections.shuffle(tweets);
        return tweets;
    }
}
