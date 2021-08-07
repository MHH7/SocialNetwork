package Model;

import Logic.DataBase;
import Logic.Reader;

import java.util.ArrayList;

public class Group {
    private final String name;
    private final ArrayList<String> users;

    public Group(String name){
        users = new ArrayList<>();
        this.name = name;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public String getName() {
        return name;
    }

    public void addMember(User user){
        String s = "";
        while (true){
            s = Reader.get().next();
            int p = 0;
            for(User u : DataBase.getDB().getAllUsers()){
                if(u.getUserName().equals(s)){
                    if(DataBase.getDB().doesFollow(user.getUserName(),s)) {
                        users.add(s);
                        p = 1;
                        break;
                    }
                }
            }
            if(p == 1){
                break;
            }
            else{
                System.out.println("The user does'nt exist!");
            }
            DataBase.getDB().Save();
        }
    }

    public void removeMember(){
        String s = "";
        while (true){
            s = Reader.get().next();
            int p = 0;
            for(int i = 0;i < users.size();i++){
                if(users.get(i).equals(s)){
                    users.remove(i);
                    p = 1;
                    break;
                }
            }
            if(p == 1){
                break;
            }
            else{
                System.out.println("The user doens'nt exist in this group!");
            }
            DataBase.getDB().Save();
        }
    }
}
