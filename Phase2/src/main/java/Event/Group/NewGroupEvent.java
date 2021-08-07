package Event.Group;

public class NewGroupEvent {
    private final String name;

    public NewGroupEvent(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
