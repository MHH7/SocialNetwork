package Logic.Pages;

import Logic.DataBase;
import Logic.Reader;
import Model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.ListIterator;
import java.util.Scanner;

public class Settings {
    static private final Logger logger = LogManager.getLogger(DataBase.class);

    public void start(User user){
        while (true) {
            System.out.println("----------Settings----------");
            System.out.println("1_Privacy Settings");
            System.out.println("2_Delete User");
            System.out.println("3_LogOut");
            System.out.println("4_Return");
            int x = 0;
            while (x < 1 || x > 4){
                x = Reader.get().nextInt();
                if(x == 1){
                    privacySettings(user);
                }
                else if(x == 2){
                    deleteUser(user);
                }
                else if(x == 3){
                    logOut(user);
                }
                else if(x == 4){
                    return;
                }
                else{
                    System.out.println("Invalid Input");
                }
                DataBase.getDB().Save();
            }
        }
    }

    public void privacySettings(User user){
        while (true){
            System.out.println("----------Privacy Settings----------");
            System.out.println("1_Change Privacy");
            System.out.println("2_Change Last Seen");
            System.out.println("3_Change Activation");
            System.out.println("4_Change Password");
            System.out.println("5_Delete Account");
            System.out.println("6_Return");
            int x = 0;
            while (x < 1 || x > 6){
                x = Reader.get().nextInt();
                if(x == 1){
                    changePrivacy(user);
                }
                else if(x == 2){
                    changeLastSeen(user);
                }
                else if(x == 3){
                    user.setActive(!user.isActive());
                }
                else if(x == 4){
                    changePassword(user);
                }
                else if(x == 5){
                    deleteUser(user);
                }
                else if(x == 6){
                    return;
                }
                else{
                    System.out.println("Invalid Password");
                }
                DataBase.getDB().Save();
            }
        }
    }

    public void deleteUser(User user){
        for(User u : DataBase.getDB().getAllUsers()){
            ListIterator<Integer> it = u.getMessages().listIterator();
            while (it.hasNext()){
                Text m = DataBase.getDB().getText(it.next());
                if(((Message)m).getReceiver().equals(user.getUserName()) || ((Message)m).getSender().equals(user.getUserName())){
                    it.remove();
                }
            }
            ListIterator<Integer> it2 = u.getTweets().listIterator();
            while (it2.hasNext()){
                Text t = DataBase.getDB().getText(it2.next());
                delete(user,(Tweet) t);
                if(((Tweet)t).getUser().equals(user.getUserName())){
                    it2.remove();
                }
            }
            ListIterator<Integer> it3 = u.getRequests().listIterator();
            while (it3.hasNext()){
                Text r = DataBase.getDB().getText(it3.next());
                if(((Request)r).get_receiver().equals(user.getUserName()) || ((Request)r).get_sender().equals(user.getUserName())){
                    it3.remove();
                }
            }
            ListIterator<Integer> it4 = u.getSaved().listIterator();
            while (it4.hasNext()){
                Text tt = DataBase.getDB().getText(it4.next());
                if(tt instanceof Tweet){
                    if(((Tweet) tt).getUser().equals(user.getUserName())){
                        it4.remove();
                    }
                }
                else if(tt instanceof Message){
                    if(((Message) tt).getReceiver().equals(user.getUserName()) || ((Message) tt).getSender().equals(user.getUserName())){
                        it4.remove();
                    }
                }
                else if(tt instanceof Request){
                    if(((Request) tt).get_receiver().equals(user.getUserName()) || ((Request) tt).get_sender().equals(user.getUserName())){
                        it4.remove();
                    }
                }
            }
            ListIterator<String> it5 = u.getFollowers().listIterator();
            while (it5.hasNext()){
                String s = it5.next();
                if(s.equals(user.getUserName())){
                    it5.remove();
                }
            }
            it5 = u.getFollowings().listIterator();
            while (it5.hasNext()){
                String s = it5.next();
                if(s.equals(user.getUserName())){
                    it5.remove();
                }
            }
            it5 = u.getBlackList().listIterator();
            while (it5.hasNext()){
                String s = it5.next();
                if(s.equals(user.getUserName())){
                    it5.remove();
                }
            }
            it5 = u.getMutes().listIterator();
            while (it5.hasNext()){
                String s = it5.next();
                if(s.equals(user.getUserName())){
                    it5.remove();
                }
            }
        }
        ListIterator<User> it6 = DataBase.getDB().getAllUsers().listIterator();
        while (it6.hasNext()){
            User uu = it6.next();
            if(uu.equals(user)){
                it6.remove();
            }
        }
        ListIterator<Text> it7 = DataBase.getDB().getAllTexts().listIterator();
        while (it7.hasNext()){
            Text m = it7.next();
            if(m instanceof Message) {
                if (((Message) m).getReceiver().equals(user.getUserName()) || ((Message) m).getSender().equals(user.getUserName())) {
                    it7.remove();
                }
            }
            else if(m instanceof Tweet){
                if(((Tweet)m).getUser().equals(user.getUserName())){
                    it7.remove();
                }
            }
            else if(m instanceof Request){
                if(((Request)m).get_receiver().equals(user.getUserName()) || ((Request)m).get_sender().equals(user.getUserName())){
                    it7.remove();
                }
            }
        }
        logger.info(user.getUserName() + " deleted account successfully!");
        DataBase.getDB().Save();
        DataBase.getDB().start();
    }

    public void delete(User user,Tweet t){
        ListIterator<Integer> it = t.getComments().listIterator();
        while (it.hasNext()){
            Text tw = DataBase.getDB().getText(it.next());
            delete(user,(Tweet) tw);
        }
        it = t.getComments().listIterator();
        while (it.hasNext()){
            Text tw = DataBase.getDB().getText(it.next());
            if(((Tweet)tw).getUser().equals(user.getUserName())){
                it.remove();
            }
        }
        DataBase.getDB().Save();
    }

    public void logOut(User user){
        user.setOnlie(false);
        user.setLastSeen(new Date());
        logger.info(user.getUserName() + " logged out of the account!");
        DataBase.getDB().start();
    }

    public void changePassword(User user){
        String password = "";
        while (!password.equals(user.getPassword())){
            System.out.print("Enter old password : ");
            password = Reader.get().next();
            if(password.equals(user.getPassword())){
                break;
            }
            else{
                System.out.println("Invalid Password!");
            }
            DataBase.getDB().Save();
        }
        System.out.print("Enter new password : ");
        password = Reader.get().next();
        user.setPassword(password);
        logger.info("Password has changed!");
        DataBase.getDB().Save();
    }

    public void changeLastSeen(User user){
        System.out.println("0_Return");
        System.out.println("1_All");
        System.out.println("2_Contacts");
        System.out.println("3_Nobody");
        int x = 0;
        while (x < 1 || x > 3){
            x = Reader.get().nextInt();
            if(x == 0){
                return;
            }
            else if(x == 1){
                user.setLastSeenType("All");
            }
            else if(x == 2){
                user.setLastSeenType("Contacts");
            }
            else if(x == 3){
                user.setLastSeenType("Nobody");
            }
            else{
                System.out.println("Invalid Password");
            }
            logger.info("Last seen settings has changed!");
            DataBase.getDB().Save();
        }
    }

    public void changePrivacy(User user){
        System.out.println("Birthdate privacy type : " + user.getBirthDatePT());
        System.out.println("Email privacy type : " + user.getEmailPT());
        System.out.println("Phone number privacy type : " + user.getPhoneNumberPT());
        System.out.println("1_Change birthdate privacy type");
        System.out.println("2_Change email privacy type");
        System.out.println("3_Change phone number privacy type");
        int x = 0;
        while(x < 1 || x > 3){
            x = Reader.get().nextInt();
            if(x == 1){
                if(user.getBirthDatePT().equals(PT.Public))user.setBirthDatePT(PT.Private);
                else user.setBirthDatePT(PT.Public);
            }
            else if(x == 2){
                if(user.getEmailPT().equals(PT.Public))user.setEmailPT(PT.Private);
                else user.setEmailPT(PT.Public);
            }
            else if(x == 3){
                if(user.getPhoneNumberPT().equals(PT.Public))user.setPhoneNumberPT(PT.Private);
                else user.setPhoneNumberPT(PT.Public);
            }
            else{
                System.out.println("Invalid Input");
            }
            logger.info("Some privacy settings probably has changed!");
            DataBase.getDB().Save();
        }
    }
}
