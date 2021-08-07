package Event.Settings.PrivacySettings;

public class PasswordChangeEvent {
    private final String password;

    public PasswordChangeEvent(String password){
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
