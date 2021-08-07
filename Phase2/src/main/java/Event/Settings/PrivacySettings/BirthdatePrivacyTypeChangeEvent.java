package Event.Settings.PrivacySettings;

import Model.PT;

public class BirthdatePrivacyTypeChangeEvent {
    private final PT type;

    public BirthdatePrivacyTypeChangeEvent(PT type){
        this.type = type;
    }

    public PT getType() {
        return type;
    }
}
