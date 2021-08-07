package Event.Login;

public class NewAccountEvent {
    private final String name;
    private final String userName;
    private final String password;
    private final String birthDate;
    private final String email;
    private final String phoneNumber;
    private final String bio;
    private final String ID;

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBio() {
        return bio;
    }

    public String getID() {
        return ID;
    }

    public NewAccountEvent(String name, String userName, String password, String birthDate, String email, String phoneNumber, String bio, String ID){
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
        this.ID = ID;
    }

}
