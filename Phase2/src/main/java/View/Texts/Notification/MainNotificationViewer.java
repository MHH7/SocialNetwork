package View.Texts.Notification;

import Controller.DataBase.DataBase;
import Model.Notification;
import View.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainNotificationViewer implements Initializable {
    private ArrayList<Integer> notifications;

    @FXML
    private VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        notifications = DataBase.getDB().getCurrentUser().getNotifications();
        for(int i = 0;i < notifications.size();i++){
            Notification notification = (Notification) DataBase.getDB().getText(notifications.get(i));
            NotificationViewer notificationViewer = new NotificationViewer(notification);
            if(!notification.isRead())vBox.getChildren().add(notificationViewer.getPane());
        }
    }

    @FXML
    void Return() throws IOException {
        MainView.getMV().back();
    }
}
