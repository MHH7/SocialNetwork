package Event.Profile;

import Model.User;

public class ReportEvent {
    private final User reporter;
    private final User reciever;

    public ReportEvent(User reporter,User reciever){
        this.reporter = reporter;
        this.reciever = reciever;
    }

    public User getReporter() {
        return reporter;
    }

    public User getReciever() {
        return reciever;
    }
}
