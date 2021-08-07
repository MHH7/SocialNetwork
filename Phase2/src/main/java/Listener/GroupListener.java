package Listener;

import Controller.Pages.PersonalPage;
import Event.Group.DeleteGroupEvent;
import Event.Group.NewGroupEvent;

public class GroupListener {
    private final PersonalPage personalPage;

    public GroupListener(){
        personalPage = new PersonalPage();
    }

    public void NewGroup(NewGroupEvent event){
        personalPage.addGroup(event.getName());
    }

    public void DeleteGroup(DeleteGroupEvent event){
        personalPage.deleteGroup(event.getName());
    }
}
