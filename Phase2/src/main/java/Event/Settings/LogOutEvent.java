package Event.Settings;

import Model.User;

public class LogOutEvent {
    private final User user;

    public LogOutEvent(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
