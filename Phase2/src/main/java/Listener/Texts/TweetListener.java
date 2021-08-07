package Listener.Texts;

import Controller.DataBase.DataBase;
import Event.Texts.Tweet.LikeEvent;
import Event.Texts.Tweet.NewTweetEvent;
import Event.Texts.Tweet.SaveEvent;
import Model.Tweet;

import java.util.ArrayList;

public class TweetListener {
    private Tweet currentTweet;
    private ArrayList<Integer> tweets;

    public void newTweet(NewTweetEvent event){
        Tweet tweet = new Tweet(event.getText(), DataBase.getDB().getCurrentUser().getUserName());
        tweet.setImage(event.getImage());
        DataBase.getDB().getCurrentUser().addTweet(tweet);
        DataBase.getDB().Save();
    }

    public void newComment(NewTweetEvent event){
        Tweet tweet = new Tweet(event.getText(), DataBase.getDB().getCurrentUser().getUserName());
        tweet.setImage(event.getImage());
        currentTweet.addComment(tweet);
        DataBase.getDB().Save();
    }

    public void setTweets(ArrayList<Integer> tweets) {
        this.tweets = tweets;
    }

    public ArrayList<Integer> getTweets() {
        return tweets;
    }

    public void setCurrentTweet(Tweet currentTweet) {
        this.currentTweet = currentTweet;
    }

    public Tweet getCurrentTweet() {
        return currentTweet;
    }

    public void like(LikeEvent event){
        event.getTweet().addLike();
    }

    public void save(SaveEvent event){
        DataBase.getDB().getCurrentUser().addSaved(event.getTweet());
        DataBase.getDB().Save();
    }
}
