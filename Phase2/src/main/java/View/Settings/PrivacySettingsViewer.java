package View.Settings;

import Controller.DataBase.DataBase;
import Event.Settings.PrivacySettings.*;
import Listener.Settings.PrivacySettingsListener;
import Model.PT;
import View.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrivacySettingsViewer implements Initializable {
    private PrivacySettingsListener privacySettingsListener;

    @FXML
    private TextField passwordText;

    @FXML
    private Label birthdatePT;

    @FXML
    private Label emailPT;

    @FXML
    private Label phoneNumberPT;

    @FXML
    private Label lastSeenPT;

    @FXML
    private Label activity;

    public Label getBirthdatePT() {
        return birthdatePT;
    }

    public Label getEmailPT() {
        return emailPT;
    }

    public Label getPhoneNumberPT() {
        return phoneNumberPT;
    }

    public Label getLastSeenPT() {
        return lastSeenPT;
    }

    public Label getActivity() {
        return activity;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        privacySettingsListener = new PrivacySettingsListener(this);
        birthdatePT.setText("Birthdate privacy type : " + DataBase.getDB().getCurrentUser().getBirthDatePT());
        emailPT.setText("Email privacy type : " + DataBase.getDB().getCurrentUser().getEmailPT());
        phoneNumberPT.setText("Phone number privacy type : " + DataBase.getDB().getCurrentUser().getPhoneNumberPT());
        lastSeenPT.setText("Last seen type : " + DataBase.getDB().getCurrentUser().getLastSeenType());
        if(DataBase.getDB().getCurrentUser().isActive()) activity.setText("Activation : Active");
        else activity.setText("Activation : Inactive");
    }

    @FXML
    void changeActivityType() {
        Boolean type;
        if(DataBase.getDB().getCurrentUser().isActive())type = false;
        else type = true;
        ActivityTypeChangeEvent event = new ActivityTypeChangeEvent(type);
        privacySettingsListener.changeActivityType(event);
    }

    @FXML
    void changeBirthdatePT() {
        PT type;
        if(DataBase.getDB().getCurrentUser().getBirthDatePT() == PT.Private)type = PT.Public;
        else type = PT.Private;
        BirthdatePrivacyTypeChangeEvent event = new BirthdatePrivacyTypeChangeEvent(type);
        privacySettingsListener.birthdatePTChange(event);
    }

    @FXML
    void changeEmailPT() {
        PT type;
        if(DataBase.getDB().getCurrentUser().getEmailPT() == PT.Private)type = PT.Public;
        else type = PT.Private;
        EmailPrivacyTypeChangeEvent event = new EmailPrivacyTypeChangeEvent(type);
        privacySettingsListener.emailPTChange(event);
    }

    @FXML
    void changeLastSeenType() {
        String type;
        if(DataBase.getDB().getCurrentUser().getLastSeenType().equals("Everybody"))type = "MyContacts";
        else if(DataBase.getDB().getCurrentUser().getLastSeenType().equals("MyContacts"))type = "Nobody";
        else type = "Everybody";
        LastSeenTypeChangeEvent event = new LastSeenTypeChangeEvent(type);
        privacySettingsListener.changeLastSeenType(event);
    }

    @FXML
    void changePhoneNumberPT() {
        PT type;
        if(DataBase.getDB().getCurrentUser().getPhoneNumberPT() == PT.Private)type = PT.Public;
        else type = PT.Private;
        PhoneNumberPrivacyTypeChangeEvent event = new PhoneNumberPrivacyTypeChangeEvent(type);
        privacySettingsListener.phoneNumberPTChange(event);
    }

    @FXML
    void passwordChange() {
        PasswordChangeEvent event = new PasswordChangeEvent(passwordText.getText());
        if(!event.getPassword().equals(""))privacySettingsListener.changePassword(event);
    }

    @FXML
    void Return() throws IOException {
        MainView.getMV().back();
    }

    @FXML
    void setProfilePhoto() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterPNG);
        File file = fileChooser.showOpenDialog(null);
        privacySettingsListener.setProfilePhoto(file.getAbsolutePath());
        DataBase.getDB().Save();
    }
}
