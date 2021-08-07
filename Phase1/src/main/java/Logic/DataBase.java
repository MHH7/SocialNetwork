package Logic;

import Logic.Pages.Login;
import Logic.Pages.Profile;
import Model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;

public class DataBase {
    static private final Logger logger = LogManager.getLogger(DataBase.class);
    private final Login login;
    private Profile profile;
    private ArrayList<User> allUsers;
    private ArrayList<String> userNames;
    private ArrayList<String> emails;
    private ArrayList<String> phoneNumbers;
    private ArrayList<String> IDs;
    private ArrayList<Text> allTexts;
    private int TextID;
    private static DataBase db;

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

    public Login getLogin() {
        return login;
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
    }

    public static DataBase getDB(){
        if(db == null)db = new DataBase();
        return db;
    }

    private DataBase() {
        login = new Login();
        profile = new Profile();
        allUsers = new ArrayList<>();
        userNames = new ArrayList<>();
        emails = new ArrayList<>();
        phoneNumbers = new ArrayList<>();
        IDs = new ArrayList<>();
        allTexts = new ArrayList<>();
    }

    public void start() {
        Load();
        if(allUsers == null)allUsers = new ArrayList<>();
        if(allTexts == null)allTexts = new ArrayList<>();
        login.preview();
    }

    public void Save(){
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            PrintStream writer = new PrintStream("./src/main/resources/Program Data/Users.json");
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
            writer = new PrintStream("./src/main/resources/Program Data/Tweets.json");
            writer.print(gson.toJson(alltweets));
            writer.close();
            writer = new PrintStream("./src/main/resources/Program Data/Messages.json");
            writer.print(gson.toJson(allmessages));
            writer.close();
            writer = new PrintStream("./src/main/resources/Program Data/Requests.json");
            writer.print(gson.toJson(allrequests));
            writer.close();
            writer = new PrintStream("./src/main/resources/Program Data/Notifications.json");
            writer.print(gson.toJson(allnotfications));
            writer.close();
            writer = new PrintStream("./src/main/resources/Program Data/TextID.json");
            writer.print(gson.toJson(TextID));
            writer.close();
            logger.info("Changes saved!");
        }catch (IOException I) {
            logger.error("Couldn't find the saving files!");
        }
    }

    public void Load(){
        try {
            allTexts = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader("./src/main/resources/Program Data/Users.json"));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            allUsers = gson.fromJson(bufferedReader,new TypeToken<ArrayList<User>>(){}.getType());
            if(allUsers == null)allUsers = new ArrayList<>();
            ArrayList<Tweet> alltweets;
            bufferedReader = new BufferedReader(new FileReader("./src/main/resources/Program Data/Tweets.json"));
            alltweets = gson.fromJson(bufferedReader,new TypeToken<ArrayList<Tweet>>(){}.getType());
            if(alltweets == null)alltweets = new ArrayList<>();
            for(Tweet t : alltweets)allTexts.add(t);
            ArrayList<Message> allmessages;
            bufferedReader = new BufferedReader(new FileReader("./src/main/resources/Program Data/Messages.json"));
            allmessages = gson.fromJson(bufferedReader,new TypeToken<ArrayList<Message>>(){}.getType());
            if(allmessages == null)allmessages = new ArrayList<>();
            for(Message m : allmessages)allTexts.add(m);
            ArrayList<Request> allrequests;
            bufferedReader = new BufferedReader(new FileReader("./src/main/resources/Program Data/Requests.json"));
            allrequests = gson.fromJson(bufferedReader,new TypeToken<ArrayList<Request>>(){}.getType());
            if(allrequests == null)allrequests = new ArrayList<>();
            for(Request r : allrequests) {
                allTexts.add(r);
                r.setSender(DataBase.getDB().getUser(r.get_sender()));
                r.setReceiver(DataBase.getDB().getUser(r.get_receiver()));
            }
            ArrayList<Notification> allnotfications;
            bufferedReader = new BufferedReader(new FileReader("./src/main/resources/Program Data/Notifications.json"));
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
            bufferedReader = new BufferedReader(new FileReader("./src/main/resources/Program Data/TextID.json"));
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
