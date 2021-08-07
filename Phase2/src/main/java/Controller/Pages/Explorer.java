package Controller.Pages;

import Controller.DataBase.DataBase;
import Model.User;

public class Explorer {
    public User search(String user){
        if(DataBase.getDB().getUser(user) == null) return null;
        else return DataBase.getDB().getUser(user);
    }
}
