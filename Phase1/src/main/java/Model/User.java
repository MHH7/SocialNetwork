package Model;

import Logic.Pages.*;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private  ArrayList<Integer> messages;
    private  ArrayList<Integer> tweets;
    private  ArrayList<Integer> requests;
    private  ArrayList<Integer> notifications;
    private  ArrayList<Integer> saved;
    private  ArrayList<String> followings;
    private  ArrayList<String> followers;
    private  ArrayList<String> blackList;
    private  ArrayList<String> mutes;
    private  ArrayList<Group> groups;
    private final String name;
    private final String userName;
    private String password;
    private final String birthDate;
    private PT birthDatePT;
    private final String email;
    private PT emailPT;
    private final String phoneNumber;
    private PT phoneNumberPT;
    private final String bio;
    private final String ID;
    private boolean isActive;
    private boolean isOnlie;
    private Date lastSeen;
    private String lastSeenType;

    public boolean groupExists(String g){
        for(Group G : groups){
            if(G.getName().equals(g))return true;
        }
        return false;
    }

    public Group getGroup(String g){
        for(Group G : groups){
            if(G.getName().equals(g))return G;
        }
        return null;
    }

    public ArrayList<Integer> getTweets() {
        if(tweets == null)tweets = new ArrayList<>();
        return tweets;
    }

    public ArrayList<Integer> getRequests() {
        if(requests == null)requests = new ArrayList<>();
        return requests;
    }

    public boolean isOnlie() {
        return isOnlie;
    }

    public ArrayList<Integer> getNotifications() {
        if(notifications == null)notifications = new ArrayList<>();
        return notifications;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getBio() {
        return bio;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public boolean isActive() {
        return isActive;
    }

    public PT getBirthDatePT() {
        return birthDatePT;
    }

    public PT getEmailPT() {
        return emailPT;
    }

    public PT getPhoneNumberPT() {
        return phoneNumberPT;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setLastSeenType(String lastSeenType) {
        this.lastSeenType = lastSeenType;
    }

    public String getLastSeenType() {
        return lastSeenType;
    }

    public void setOnlie(boolean b){
        isOnlie = b;
    }

    public void setActive(boolean b){
        isActive = b;
    }

    public void setLastSeen(Date d){
        lastSeen = d;
    }

    public void setBirthDatePT(PT birthDatePT) {
        this.birthDatePT = birthDatePT;
    }

    public void setEmailPT(PT emailPT) {
        this.emailPT = emailPT;
    }

    public void setPhoneNumberPT(PT phoneNumberPT) {
        this.phoneNumberPT = phoneNumberPT;
    }

    public void addMessage(Message message){
        messages.add(message.getID());
    }

    public void addTweet(Tweet tweet){
        tweets.add(tweet.getID());
    }

    public void addSaved(Text text){
        saved.add(text.getID());
    }

    public ArrayList<Integer> getSaved() {
        if(saved == null)saved = new ArrayList<>();
        return saved;
    }

    public ArrayList<Integer> getMessages() {
        if(messages == null)messages = new ArrayList<>();
        return messages;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName(){
        return userName;
    }

    public String getID() {
        return ID;
    }

    public ArrayList<String> getBlackList() {
        if(blackList == null)blackList = new ArrayList<>();
        return blackList;
    }

    public ArrayList<String> getFollowers() {
        if(followers == null)followers = new ArrayList<>();
        return followers;
    }

    public ArrayList<String> getFollowings() {
        if(followings == null)followings = new ArrayList<>();
        return followings;
    }

    public ArrayList<String> getMutes() {
        if(mutes == null)mutes = new ArrayList<>();
        return mutes;
    }

    public ArrayList<Group> getGroups() {
        if(groups == null)groups = new ArrayList<>();
        return groups;
    }

    public User(String name, String userName, String password, String birthDate, String email, String phoneNumber, String bio, String ID){
        messages = new ArrayList<>();
        tweets = new ArrayList<>();
        requests = new ArrayList<>();
        notifications = new ArrayList<>();
        saved = new ArrayList<>();
        followings = new ArrayList<>();
        followers = new ArrayList<>();
        blackList = new ArrayList<>();
        mutes = new ArrayList<>();
        groups = new ArrayList<>();
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.birthDate = birthDate;
        birthDatePT = PT.Public;
        this.email = email;
        emailPT = PT.Public;
        this.phoneNumber = phoneNumber;
        phoneNumberPT = PT.Public;
        this.bio = bio;
        this.ID = ID;
        isActive = true;
        isOnlie = true;
        lastSeen = new Date();
        lastSeenType = "All";
    }
}
