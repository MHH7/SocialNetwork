package Event.Settings.PrivacySettings;

import Model.PT;

public class EmailPrivacyTypeChangeEvent {
    private final PT type;

    public EmailPrivacyTypeChangeEvent(PT type){
        this.type = type;
    }

    public PT getType() {
        return type;
    }
}
