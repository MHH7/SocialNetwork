package Logic.Pages;

import Logic.DataBase;
import Logic.Reader;
import Model.User;

import java.util.Scanner;

public class Explorer {
    private final TimeLine timeLine;

    public Explorer(TimeLine timeLine){
        this.timeLine = timeLine;
    }

    public void start(User user){
        while (true) {
            System.out.println("----------Explorer----------");
            System.out.println("1_Search");
            System.out.println("2_TimeLine");
            System.out.println("3_Return");
            int x = 0;
            while (x < 1 || x > 3){
                x = Reader.get().nextInt();
                if(x == 1){
                    search(user);
                }
                else if(x == 2){
                    timeLine.start(user);
                }
                else if(x == 3){
                    return;
                }
                else{
                    System.out.println("Invalid Input");
                }
                DataBase.getDB().Save();
            }
        }
    }

    public void search(User user){
        System.out.println("----------Search----------");
        System.out.println("Enter user you looking for : ");
        String s = Reader.get().nextLine();
        if(DataBase.getDB().getUser(s) == null) System.out.println("User does not exist!");
        else DataBase.getDB().getProfile().open(DataBase.getDB().getUser(s),user);
    }
}
