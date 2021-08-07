package Event.Settings.PrivacySettings;

import Controller.Pages.Settings;
import Model.PT;

public class PhoneNumberPrivacyTypeChangeEvent {
    private final PT type;

    public PhoneNumberPrivacyTypeChangeEvent(PT type){
        this.type = type;
    }

    public PT getType() {
        return type;
    }
}
