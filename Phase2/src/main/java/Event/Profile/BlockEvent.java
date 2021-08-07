package Event.Profile;

import Model.User;

public class BlockEvent {
    private final User blocker;
    private final User reciever;
    private final boolean state;

    public BlockEvent(User blocker,User reciever,boolean state){
        this.blocker = blocker;
        this.reciever = reciever;
        this.state = state;
    }

    public User getBlocker() {
        return blocker;
    }

    public User getReciever() {
        return reciever;
    }

    public boolean getState(){
        return state;
    }
}
