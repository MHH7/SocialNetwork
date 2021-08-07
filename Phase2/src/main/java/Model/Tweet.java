package Model;

import Controller.DataBase.DataBase;
import Controller.Pages.Login;
import Controller.Pages.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;

public class Tweet extends Text implements Cloneable{
    private ArrayList<Integer> comments;
    private final String user;
    private int likes;
    private String retweet;
    private String forward;
    private String image;
    private int reports;
    static private final Logger logger = LogManager.getLogger(Tweet.class);

    public void addLike(){
        likes++;
        logger.info(DataBase.getDB().getCurrentUser().getUserName() + " liked Tweet " + ID);
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public String getImagePhoto() throws MalformedURLException {
        File file = new File(image);
        return file.toURI().toURL().toExternalForm();
    }

    public void reprt(){
        reports++;
        logger.info(DataBase.getDB().getCurrentUser() + " reported Tweet " + ID);
        if(reports == 0){
            Settings settings = new Settings();
            settings.deleteTweet(this);
        }
        DataBase.getDB().Save();
    }

    public int getReports() {
        return reports;
    }

    public void addComment(Tweet tweet){
        comments.add(tweet.getID());
    }

    public String getUser() {
        return user;
    }

    public ArrayList<Integer> getComments() {
        if(comments == null)comments = new ArrayList<>();
        return comments;
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
        image = "";
    }

    @Override
    public String show() {
        String showText = "";
        if(!getForward().equals("")){
            showText += ("Forwarded from : " + getForward());
            showText += "\n";
        }
        if(!getRetweet().equals("")){
            showText += ("Retweeted by : " + getRetweet());
            showText += "\n";
        }
        Date d = new Date();
        showText += (user + " " + d);
        showText += "\n";
        showText += super.show();
        return showText;
    }

    public String open(){
        String showText = "";
        if (!getForward().equals("")){
            showText += ("Forwarded from : " + getForward());
            showText += "\n";
        }
        if (!getRetweet().equals("")){
            showText += ("Retweeted by : " + getRetweet());
            showText += "\n";
        }
        isRead = true;
        showText += (DataBase.getDB().getUser(user).getUserName() + " " + DataBase.getDB().getUser(user).getID() + " " + date);
        showText += "\n";
        showText += super.show();
        showText += (likes + " Likes");
        showText += "\n";

        DataBase.getDB().Save();
        return showText;
    }

    public void reTweet(){
        Tweet t = this.clone();
        t.setRetweet(this.getUser());
        t.setID(DataBase.getDB().getTextID());
        DataBase.getDB().addText(t);
        DataBase.getDB().getCurrentUser().getTweets().add(t.getID());
        logger.info(DataBase.getDB().getCurrentUser().getUserName() + " retweeted Tweet " + ID);
        DataBase.getDB().Save();
    }

    public void forward(String to){
        Tweet t = this.clone();
        t.setForward(this.getUser());
        t.setID(DataBase.getDB().getTextID());
        DataBase.getDB().addText(t);
        logger.info(DataBase.getDB().getCurrentUser().getUserName() + " forwarded Tweet " + ID + " to " + to);
        DataBase.getDB().getUser(to).getMessages().add(t.getID());
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
