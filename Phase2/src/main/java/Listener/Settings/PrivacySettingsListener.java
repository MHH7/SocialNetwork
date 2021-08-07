package Listener.Settings;

import Controller.DataBase.DataBase;
import Event.Settings.PrivacySettings.*;
import View.Settings.PrivacySettingsViewer;

public class PrivacySettingsListener {
    private final PrivacySettingsViewer privacySettingsViewer;

    public PrivacySettingsListener(PrivacySettingsViewer privacySettingsViewer){
        this.privacySettingsViewer = privacySettingsViewer;
    }

    public void phoneNumberPTChange(PhoneNumberPrivacyTypeChangeEvent event){
        DataBase.getDB().getCurrentUser().setPhoneNumberPT(event.getType());
        privacySettingsViewer.getPhoneNumberPT().setText("Phone number privacy type : " + DataBase.getDB().getCurrentUser().getPhoneNumberPT());
        DataBase.getDB().Save();
    }

    public void emailPTChange(EmailPrivacyTypeChangeEvent event){
        DataBase.getDB().getCurrentUser().setEmailPT(event.getType());
        privacySettingsViewer.getEmailPT().setText("Email privacy type : " + DataBase.getDB().getCurrentUser().getEmailPT());
        DataBase.getDB().Save();
    }

    public void birthdatePTChange(BirthdatePrivacyTypeChangeEvent event){
        DataBase.getDB().getCurrentUser().setBirthDatePT(event.getType());
        privacySettingsViewer.getBirthdatePT().setText("Birthdate privacy type : " + DataBase.getDB().getCurrentUser().getBirthDatePT());
        DataBase.getDB().Save();
    }

    public void changeActivityType(ActivityTypeChangeEvent event){
        DataBase.getDB().getCurrentUser().setActive(event.getType());
        if(DataBase.getDB().getCurrentUser().isActive()) privacySettingsViewer.getActivity().setText("Activation : Active");
        else privacySettingsViewer.getActivity().setText("Activation : Inactive");
        DataBase.getDB().Save();
    }

    public void changeLastSeenType(LastSeenTypeChangeEvent event){
        DataBase.getDB().getCurrentUser().setLastSeenType(event.getType());
        privacySettingsViewer.getLastSeenPT().setText("Last seen type : " + DataBase.getDB().getCurrentUser().getLastSeenType());
        DataBase.getDB().Save();
    }

    public void changePassword(PasswordChangeEvent event){
        DataBase.getDB().getCurrentUser().setPassword(event.getPassword());
        DataBase.getDB().Save();
    }

    public void setProfilePhoto(String path){
        DataBase.getDB().getCurrentUser().setProfilePhoto(path);
    }
}
