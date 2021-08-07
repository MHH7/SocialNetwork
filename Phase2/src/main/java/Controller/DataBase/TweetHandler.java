package Controller.DataBase;

import Listener.Texts.TweetListener;

import java.util.Stack;

public class TweetHandler {
    private final Stack<TweetListener> stack;

    public TweetHandler(){
        stack = new Stack<>();
        stack.push(new TweetListener());
        stack.lastElement().setTweets(DataBase.getDB().getCurrentUser().getTweets());
    }

    public void back(){
        stack.pop();
        if(stack.size() == 0){
            stack.push(new TweetListener());
            stack.lastElement().setTweets(DataBase.getDB().getCurrentUser().getTweets());
        }
    }

    public Stack<TweetListener> getStack() {
        return stack;
    }

    public TweetListener getTweetListener(){
        return stack.lastElement();
    }
}
