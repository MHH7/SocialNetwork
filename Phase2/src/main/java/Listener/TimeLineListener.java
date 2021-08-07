package Listener;

import Controller.Pages.TimeLine;

import java.util.ArrayList;

public class TimeLineListener {
    private final TimeLine timeLine;

    public TimeLineListener(){
        timeLine = new TimeLine();
    }

    public ArrayList<Integer> getTweets(){
        return timeLine.getList();
    }
}
