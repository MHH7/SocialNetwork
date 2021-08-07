package Event.Login;

public class LoadAccountEvent {
    private final String password;
    private final String userName;

    public LoadAccountEvent(String userName,String password){
        this.password = password;
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }
}
