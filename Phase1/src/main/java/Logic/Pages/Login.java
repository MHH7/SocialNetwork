package Logic.Pages;

import Logic.DataBase;
import Logic.Reader;
import Model.User;
import com.sun.source.tree.WhileLoopTree;
import com.sun.tools.javac.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.Scanner;

public class Login {
    private final MainPage mainPage;
    static private final Logger logger = LogManager.getLogger(MainPage.class);

    public MainPage getMainPage() {
        return mainPage;
    }

    public Login(){
        System.out.println("----------Login----------");
        mainPage = new MainPage();
    }

    public void preview(){
        System.out.println("Already have an acount?");
        System.out.println("1_YES");
        System.out.println("2_NO");
        int x = 0;
        User user = null;
        while (x != 1 && x != 2){
            x = Reader.get().nextInt();
            if(x == 1){
                user = loadAccount();
            }
            else if(x == 2){
                user = createNewAccount();
            }
            else{
                System.out.println("Invalid Input");
            }
        }
        DataBase.getDB().Save();
        mainPage.start(user);
    }

    private User loadAccount(){
        String userName = "";
        while(!DataBase.getDB().isUserNameUsed(userName)){
            System.out.print("Username : ");
            userName = Reader.get().next();
            if(!DataBase.getDB().isUserNameUsed(userName)) System.out.println("User doesnt exist!");
        }
        String password = "";
        while (!password.equals(DataBase.getDB().getUser(userName).getPassword())){
            System.out.print("Password : ");
            password = Reader.get().next();
            if(!password.equals(DataBase.getDB().getUser(userName).getPassword())) System.out.println("Wrong password!");
        }
        DataBase.getDB().getUser(userName).setLastSeen(new Date());
        return DataBase.getDB().getUser(userName);
    }

    private User createNewAccount(){
        System.out.println("----------New Account----------");
        System.out.print("Name : ");
        String name = Reader.get().next();
        String userName = "";
        while(DataBase.getDB().isUserNameUsed(userName) || userName.equals("")){
            System.out.print("Username : ");
            userName = Reader.get().next();
            if(DataBase.getDB().isUserNameUsed(userName)) System.out.println("This username has already been taken!");
        }
        String password = "";
        System.out.print("Password : ");
        password = Reader.get().next();
        String birthDate = "";
        System.out.print("Birth Date : ");
        birthDate = Reader.get().next();
        String email = "";
        while (DataBase.getDB().isEmailUsed(email) || email.equals("")) {
            System.out.print("Email : ");
            email = Reader.get().next();
            if(DataBase.getDB().isEmailUsed(email)) System.out.println("This email had already been taken!");
        }
        String phoneNumber = "";
        while (DataBase.getDB().isPhoneNumberUsed(phoneNumber) || phoneNumber.equals("")) {
            System.out.print("Phone Number : ");
            phoneNumber = Reader.get().next();
            if(DataBase.getDB().isPhoneNumberUsed(phoneNumber)) System.out.println("This phone number has already been taken!");
        }
        String bio = "";
        System.out.print("Bio : ");
        bio = Reader.get().nextLine();
        String ID = "";
        while (DataBase.getDB().isIDUsed(phoneNumber) || ID.equals("")) {
            System.out.print("ID : ");
            ID = Reader.get().next();
            if(DataBase.getDB().isIDUsed(phoneNumber)) System.out.println("This ID has already been taken!");
        }
        User u = new User(name,userName,password,birthDate,email,phoneNumber,bio,ID);
        DataBase.getDB().addUser(u);
        DataBase.getDB().Save();
        logger.info(userName + " created new accout!");
        return u;
    }
}
