package Listener;

import Controller.Pages.Login;
import Event.Login.LoadAccountEvent;
import Event.Login.NewAccountEvent;
import View.LoginViewer;

public class LoginListener {
    private final LoginViewer loginViewer;
    private final Login login;

    public LoginListener(LoginViewer loginViewer){
        this.loginViewer = loginViewer;
        login = new Login();
    }

    public boolean createNewAccount(NewAccountEvent event){
        return login.createNewAccount(event);
    }

    public boolean loadAccount(LoadAccountEvent event){
        return login.loadAccount(event);
    }
}
