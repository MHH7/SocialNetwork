package Controller.DataBase;

import Config.Config;
import Controller.Pages.Profile;
import Model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;

public class DataBase{
    static private final Logger logger = LogManager.getLogger(DataBase.class);
    private Profile profile;
    private ArrayList<User> allUsers;
    private ArrayList<String> userNames;
    private ArrayList<String> emails;
    private ArrayList<String> phoneNumbers;
    private ArrayList<String> IDs;
    private ArrayList<ChatRoom> chatRooms;
    private ArrayList<Text> allTexts;
    private User currentUser;
    private User profileUser;
    private ArrayList<User> chatUsers;
    private TweetHandler tweetHandler;
    private Request currentRequest;
    private Message currentMessage;
    private ChatRoom currentChatRoom;
    private int TextID;
    private static DataBase db;

    private DataBase() {
        profile = new Profile();
        allUsers = new ArrayList<>();
        userNames = new ArrayList<>();
        emails = new ArrayList<>();
        phoneNumbers = new ArrayList<>();
        IDs = new ArrayList<>();
        allTexts = new ArrayList<>();
        chatRooms = new ArrayList<>();
    }

    public ArrayList<ChatRoom> getChatRooms() {
        if(chatRooms == null)chatRooms = new ArrayList<>();
        return chatRooms;
    }

    public void addChatRoom(ChatRoom chatRoom){
        if(chatRooms == null)chatRooms = new ArrayList<>();
        for(ChatRoom c : chatRooms)if(c.getName().equals(chatRoom.getName()))return;
        chatRooms.add(chatRoom);
        logger.info("New Chat Room " + chatRoom.getName() + " created!");
        DataBase.getDB().Save();
    }

    public void setCurrentChatRoom(ChatRoom currentChatRoom) {
        this.currentChatRoom = currentChatRoom;
    }

    public ChatRoom getCurrentChatRoom() {
        return currentChatRoom;
    }

    public void setCurrentMessage(Message currentMessage) {
        this.currentMessage = currentMessage;
    }

    public Message getCurrentMessage() {
        return currentMessage;
    }

    public void addChatUsers(User user) {
        chatUsers.add(user);
    }

    public void newChatUsers(){
        chatUsers = new ArrayList<>();
    }

    public ArrayList<User> getChatUsers() {
        return chatUsers;
    }

    public void setCurrentRequest(Request currentRequest) {
        this.currentRequest = currentRequest;
    }

    public Request getCurrentRequest() {
        return currentRequest;
    }

    public TweetHandler getTweetHandler() {
        return tweetHandler;
    }

    public void setProfileUser(User profileUser) {
        this.profileUser = profileUser;
    }

    public User getProfileUser() {
        return profileUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        tweetHandler = new TweetHandler();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Profile getProfile() {
        return profile;
    }

    public ArrayList<Text> getAllTexts() {
        return allTexts;
    }

    public User getUser(String user) {
        for (User u : allUsers) if (u.getUserName().equals(user)) return u;
        return null;
    }

    public void addUserName(String s) {
        userNames.add(s);
    }

    public void addEmail(String s) {
        emails.add(s);
    }

    public void addPhoneNumber(String s) {
        phoneNumbers.add(s);
    }

    public void addID(String s) {
        IDs.add(s);
    }

    public void addUser(User u) {
        allUsers.add(u);
        addID(u.getID());
        addPhoneNumber(u.getPhoneNumber());
        addEmail(u.getEmail());
        addUserName(u.getUserName());
    }

    public ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public boolean isUserNameUsed(String s) {
        for (String S : userNames) if (s.equals(S)) return true;
        return false;
    }

    public boolean isEmailUsed(String s) {
        for (String S : emails) if (s.equals(S)) return true;
        return false;
    }

    public boolean isPhoneNumberUsed(String s) {
        for (String S : phoneNumbers) if (s.equals(S)) return true;
        return false;
    }

    public boolean isIDUsed(String s) {
        for (String S : IDs) if (s.equals(S)) return true;
        return false;
    }

    public boolean doesFollow(String a, String b) {
        //returns true if a follows b
        for (String s : getUser(a).getFollowings()) if (s.equals(b)) return true;
        return false;
    }

    public boolean doesBlock(String a, String b) {
        //returns true if a blocks b
        for (String s : getUser(a).getBlackList()) if (s.equals(b)) return true;
        return false;
    }

    public Text getText(int ID){
        for(Text t: allTexts){
            if(t.getID() == ID)return t;
        }
        return null;
    }

    public int getTextID(){
        TextID++;
        return TextID;
    }

    public void addText(Text text){
        allTexts.add(text);
        DataBase.getDB().Save();
    }

    public static DataBase getDB(){
        if(db == null)db = new DataBase();
        return db;
    }

    public void start() {
        Load();
        if(allUsers == null)allUsers = new ArrayList<>();
        if(allTexts == null)allTexts = new ArrayList<>();
    }

    public void Save(){
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            PrintStream writer = new PrintStream(Config.getConfig("JSONFiles").getProperty(String.class,"Users"));
            writer.print(gson.toJson(allUsers));
            writer.close();
            ArrayList<Tweet> alltweets = new ArrayList<>();
            ArrayList<Message> allmessages = new ArrayList<>();
            ArrayList<Request> allrequests = new ArrayList<>();
            ArrayList<Notification> allnotfications = new ArrayList<>();
            for(Text t : allTexts){
                if(t instanceof Tweet)alltweets.add((Tweet) t);
                else if(t instanceof Message)allmessages.add((Message) t);
                else if(t instanceof Request)allrequests.add((Request) t);
                else if(t instanceof Notification)allnotfications.add((Notification) t);
            }
            writer = new PrintStream(Config.getConfig("JSONFiles").getProperty(String.class,"Tweets"));
            writer.print(gson.toJson(alltweets));
            writer.close();
            writer = new PrintStream(Config.getConfig("JSONFiles").getProperty(String.class,"Messages"));
            writer.print(gson.toJson(allmessages));
            writer.close();
            writer = new PrintStream(Config.getConfig("JSONFiles").getProperty(String.class,"Requests"));
            writer.print(gson.toJson(allrequests));
            writer.close();
            writer = new PrintStream(Config.getConfig("JSONFiles").getProperty(String.class,"Notifications"));
            writer.print(gson.toJson(allnotfications));
            writer.close();
            writer = new PrintStream(Config.getConfig("JSONFiles").getProperty(String.class,"TextID"));
            writer.print(gson.toJson(TextID));
            writer.close();
            writer = new PrintStream(Config.getConfig("JSONFiles").getProperty(String.class,"ChatRooms"));
            writer.print(gson.toJson(chatRooms));
            writer.close();
            logger.info("Changes saved!");
        }catch (IOException I) {
            logger.error("Couldn't find the saving files!");
        }
    }

    public void Load(){
        try {
            allTexts = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(Config.getConfig("JSONFiles").getProperty(String.class,"Users")));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            allUsers = gson.fromJson(bufferedReader,new TypeToken<ArrayList<User>>(){}.getType());
            if(allUsers == null)allUsers = new ArrayList<>();
            ArrayList<Tweet> alltweets;
            bufferedReader = new BufferedReader(new FileReader(Config.getConfig("JSONFiles").getProperty(String.class,"Tweets")));
            alltweets = gson.fromJson(bufferedReader,new TypeToken<ArrayList<Tweet>>(){}.getType());
            if(alltweets == null)alltweets = new ArrayList<>();
            for(Tweet t : alltweets)allTexts.add(t);
            ArrayList<Message> allmessages;
            bufferedReader = new BufferedReader(new FileReader(Config.getConfig("JSONFiles").getProperty(String.class,"Messages")));
            allmessages = gson.fromJson(bufferedReader,new TypeToken<ArrayList<Message>>(){}.getType());
            if(allmessages == null)allmessages = new ArrayList<>();
            for(Message m : allmessages)allTexts.add(m);
            ArrayList<Request> allrequests;
            bufferedReader = new BufferedReader(new FileReader(Config.getConfig("JSONFiles").getProperty(String.class,"Requests")));
            allrequests = gson.fromJson(bufferedReader,new TypeToken<ArrayList<Request>>(){}.getType());
            if(allrequests == null)allrequests = new ArrayList<>();
            bufferedReader = new BufferedReader(new FileReader(Config.getConfig("JSONFiles").getProperty(String.class,"ChatRooms")));
            chatRooms = gson.fromJson(bufferedReader,new TypeToken<ArrayList<ChatRoom>>(){}.getType());
            if(chatRooms == null)chatRooms = new ArrayList<>();
            if(allrequests == null)allrequests = new ArrayList<>();
            for(Request r : allrequests) {
                allTexts.add(r);
                r.setSender(DataBase.getDB().getUser(r.get_sender()));
                r.setReceiver(DataBase.getDB().getUser(r.get_receiver()));
            }
            ArrayList<Notification> allnotfications;
            bufferedReader = new BufferedReader(new FileReader(Config.getConfig("JSONFiles").getProperty(String.class,"Notifications")));
            allnotfications = gson.fromJson(bufferedReader,new TypeToken<ArrayList<Notification>>(){}.getType());
            if(allnotfications == null)allnotfications = new ArrayList<>();
            for(Notification n : allnotfications)allTexts.add(n);
            for(int i = 0;i < allTexts.size();i++){
                for(int j = i + 1;j < allTexts.size();j++){
                    if(allTexts.get(j).getID() < allTexts.get(j).getID()){
                        Text t = allrequests.get(j);
                        allTexts.set(j,allTexts.get(i));
                        allTexts.set(i,t);
                    }
                }
            }
            bufferedReader = new BufferedReader(new FileReader(Config.getConfig("JSONFiles").getProperty(String.class,"TextID")));
            TextID = gson.fromJson(bufferedReader,int.class);
            for(User u : allUsers){
                userNames.add(u.getUserName());
                phoneNumbers.add(u.getPhoneNumber());
                emails.add(u.getEmail());
                IDs.add(u.getID());
            }
            logger.info("Information loaded!");
        }catch (FileNotFoundException F){
            logger.info("Couldn't find the loading files!");
        }
    }
}
