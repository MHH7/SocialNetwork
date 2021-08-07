package View.PersonalPage;

import Controller.DataBase.DataBase;
import Event.Group.DeleteGroupEvent;
import Event.Group.NewGroupEvent;
import Listener.GroupListener;
import Listener.PersonalPageListener;
import Model.Group;
import View.MainView;
import View.Profile.MiniProfileViewer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ContactsGroupViewer implements Initializable {
    private GroupListener groupListener;
    private PersonalPageListener personalPageListener;

    @FXML
    private VBox vBox1;

    @FXML
    private VBox vBox2;

    @FXML
    private TextField newGroupTextBox;

    @FXML
    private TextField deleteGroupTextBox;

    @FXML
    private TextField userAddTextBox;

    @FXML
    private TextField toGroupAddTextBox;

    @FXML
    private TextField userRemoveTextBox;

    @FXML
    private TextField fromGroupRemoveTextBox;

    @FXML
    private TextField showGroupNameTextBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        groupListener = new GroupListener();
        personalPageListener  = new PersonalPageListener();
        personalPageListener.showFollowers(vBox1);
    }

    @FXML
    void Return() throws IOException {
        MainView.getMV().back();
    }

    @FXML
    void addUserToGroup() {
        DataBase.getDB().getCurrentUser().getGroup(toGroupAddTextBox.getText()).addMember(userAddTextBox.getText());
        DataBase.getDB().Save();
    }

    @FXML
    void createNewGroup() {
        NewGroupEvent event = new NewGroupEvent(newGroupTextBox.getText());
        groupListener.NewGroup(event);
    }

    @FXML
    void deleteGroup() {
        DeleteGroupEvent event = new DeleteGroupEvent(deleteGroupTextBox.getText());
        groupListener.DeleteGroup(event);
    }

    @FXML
    void removeUserFromGroup() {
        DataBase.getDB().getCurrentUser().getGroup(fromGroupRemoveTextBox.getText()).removeMember(userRemoveTextBox.getText());
        DataBase.getDB().Save();
    }

    @FXML
    void show() {
        vBox2.getChildren().clear();
        ArrayList<String> users = DataBase.getDB().getCurrentUser().getGroup(showGroupNameTextBox.getText()).getUsers();
        for(String user : users){
            MiniProfileViewer miniProfileViewer = new MiniProfileViewer(DataBase.getDB().getUser(user));
            vBox2.getChildren().add(miniProfileViewer.getPane());
        }
    }

    @FXML
    void showAllGroups() {
        vBox2.getChildren().clear();
        String text = "";
        Label label = new Label();
        ArrayList<Group> groups = DataBase.getDB().getCurrentUser().getGroups();
        for(Group g : groups){
            text += g.getName();
            text += "\n";
            text += g.getUsers().size();
            text += "\n";
            text += "\n";
        }
        label.setText(text);
        label.setVisible(true);
        vBox2.getChildren().add(label);
    }
}
