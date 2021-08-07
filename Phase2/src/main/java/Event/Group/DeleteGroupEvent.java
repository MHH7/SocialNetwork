package Event.Group;

public class DeleteGroupEvent {
    private final String name;

    public DeleteGroupEvent(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
