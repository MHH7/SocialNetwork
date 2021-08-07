package Model;

import Logic.DataBase;

public class Notification extends Text{
    public Notification(String text){
        super(text);
        DataBase.getDB().addText(this);
    }
}
