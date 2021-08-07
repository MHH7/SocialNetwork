package Listener.Settings;

import Controller.Pages.Settings;
import Event.Login.LoadAccountEvent;
import Event.Settings.DeleteAccountEvent;
import Event.Settings.LogOutEvent;
import View.Settings.SettingsViewer;

public class SettingsListener {
    private final Settings settings;
    private final SettingsViewer settingsViewer;

    public SettingsListener(SettingsViewer settingsViewer){
        this.settingsViewer = settingsViewer;
        settings = new Settings();
    }

    public void deleteAccount(DeleteAccountEvent event){
        settings.deleteUser(event.getUser());
    }

    public void logOutAccount(LogOutEvent event){
        settings.logOut(event.getUser());
    }

}
