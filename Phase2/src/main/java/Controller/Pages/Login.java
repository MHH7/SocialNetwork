package Controller.Pages;

import Controller.DataBase.DataBase;
import Event.Login.LoadAccountEvent;
import Event.Login.NewAccountEvent;
import Graphic.Main;
import Model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Login {
    static private final Logger logger = LogManager.getLogger(Login.class);

    public boolean createNewAccount(NewAccountEvent event){
        if(event.getPassword().equals(""))return false;
        if(event.getEmail().equals(""))return false;
        if(event.getID().equals(""))return false;
        if(event.getPhoneNumber().equals(""))return false;
        if(event.getUserName().equals(""))return false;
        if(event.getBio().equals(""))return false;
        if(event.getBirthDate().equals(""))return false;
        if(event.getName().equals(""))return false;
        if(DataBase.getDB().isEmailUsed(event.getEmail()))return false;
        if(DataBase.getDB().isIDUsed(event.getID()))return false;
        if(DataBase.getDB().isUserNameUsed(event.getUserName()))return false;
        if(DataBase.getDB().isPhoneNumberUsed(event.getPhoneNumber()))return false;
        User u = new User(event.getName(), event.getUserName(), event.getPassword(), event.getBirthDate(),
                event.getEmail(), event.getPhoneNumber(), event.getBio(), event.getID());
        logger.info(u.getUserName() + " created account!");
        DataBase.getDB().setCurrentUser(u);
        DataBase.getDB().addUser(u);
        DataBase.getDB().Save();
        return true;
    }

    public boolean loadAccount(LoadAccountEvent event){
        if(event.getPassword().equals(""))return false;
        if(event.getUserName().equals(""))return false;
        if(DataBase.getDB().getUser(event.getUserName()) == null)return false;
        if(!DataBase.getDB().getUser(event.getUserName()).getPassword().equals(event.getPassword()))return false;
        User u = DataBase.getDB().getUser(event.getUserName());
        logger.info(u.getUserName() + " logged into account!");
        DataBase.getDB().setCurrentUser(u);
        DataBase.getDB().Save();
        return true;
    }
}
