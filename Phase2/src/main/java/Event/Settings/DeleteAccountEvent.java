package Event.Settings;

import Model.User;

public class DeleteAccountEvent {
    private final User user;

    public DeleteAccountEvent(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
