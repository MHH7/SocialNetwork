package Model;

import Controller.DataBase.DataBase;
import Controller.Pages.Login;
import Controller.Pages.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.MalformedURLException;

public class Message extends Text{
    private final String sender;
    private String receiver;
    private String image;
    private String chatRoomName;
    static private final Logger logger = LogManager.getLogger(Message.class);

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

    public void edit(String newText){
        text = newText;
        logger.info("Message " + ID + " edited!");
        DataBase.getDB().Save();
    }

    public void delete(){
        Settings settings = new Settings();
        settings.deleteMessage(this);
        DataBase.getDB().Save();
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public Message(String sender, String receiver,String text){
        super(text);
        image = "";
        DataBase.getDB().addText(this);
        this.sender = sender;
        this.receiver = receiver;
    }

    public Message(String sender,String text){
        super(text);
        this.sender = sender;
        image = "";
        DataBase.getDB().addText(this);
    }

    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    @Override
    public String show() {
        String showText = "";
        if(receiver != null) showText += ("From : " + sender + " to : " + receiver);
        else showText += ("From : " + sender + " to : " + chatRoomName);
        showText += "\n";
        showText += super.show();
        showText += (date);
        showText += "\n";
        return showText;
    }

    public void save(){
        DataBase.getDB().getCurrentUser().addSaved(this);
        DataBase.getDB().Save();
    }
}
