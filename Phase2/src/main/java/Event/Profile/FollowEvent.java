package Event.Profile;

import Model.User;

public class FollowEvent {
    private final User follower;
    private final User reciever;
    private final boolean state;

    public FollowEvent(User follower,User reciever,boolean state){
        this.follower = follower;
        this.reciever = reciever;
        this.state = state;
    }

    public User getFollower() {
        return follower;
    }

    public User getReciever() {
        return reciever;
    }

    public boolean getState(){
        return state;
    }
}
