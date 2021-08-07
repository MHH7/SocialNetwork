package Model;

import Controller.DataBase.DataBase;

import java.util.Date;

public class Text {
    protected String text;
    protected boolean isRead;
    protected Date date;
    protected int ID;

    public Text(String text){
        this.text = text;
        isRead = false;
        date = new Date();
        ID = DataBase.getDB().getTextID();
    }

    public int getID(){
        return ID;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String show(){
        String showText = "";
        showText += (text);
        showText += "\n";
        return showText;
    }

    public void open(User user){

    }
}
