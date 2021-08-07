package Event.Settings.PrivacySettings;

public class ActivityTypeChangeEvent {
    private final Boolean type;

    public ActivityTypeChangeEvent(Boolean type){
        this.type = type;
    }

    public Boolean getType() {
        return type;
    }
}
