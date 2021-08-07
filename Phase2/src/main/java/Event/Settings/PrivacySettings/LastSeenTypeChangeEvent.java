package Event.Settings.PrivacySettings;

public class LastSeenTypeChangeEvent {
    private final String type;

    public LastSeenTypeChangeEvent(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
