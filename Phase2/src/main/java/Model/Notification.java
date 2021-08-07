package Model;

import Controller.DataBase.DataBase;

public class Notification extends Text{
    public Notification(String text){
        super(text);
        DataBase.getDB().addText(this);
    }
}
