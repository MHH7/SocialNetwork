package View.Messenger;

import Controller.DataBase.DataBase;
import Listener.MessengerListener;
import Model.User;
import View.MainView;
import View.Profile.MiniProfileViewer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InboxViewer implements Initializable {
    private MessengerListener messengerListener;

    @FXML
    private VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messengerListener = new MessengerListener();
        ArrayList<User> users = messengerListener.getInbox();
        for(User u : users){
            MiniProfileViewer miniProfileViewer = new MiniProfileViewer(u);
            Pane pane = miniProfileViewer.getPane();
            int cnt =  messengerListener.getUnseen(u);
            if(cnt != 0 || u.getUserName().equals(DataBase.getDB().getCurrentUser().getUserName())) {
                Label cntLabel = new Label();
                cntLabel.setLayoutX(pane.getLayoutX());
                cntLabel.setLayoutY(pane.getLayoutY() + 50);
                String wtf = "";
                while (cnt > 0) {
                    wtf += (char)('0' + (cnt % 10));
                    cnt /= 10;
                }
                String wtf2 = "";
                for (int i = wtf.length() - 1; i >= 0; i--) wtf2 += wtf.charAt(i);
                cntLabel.setText(wtf2);
                cntLabel.setVisible(true);
                pane.getChildren().add(cntLabel);
            }
            vBox.getChildren().add(pane);
        }

    }

    @FXML
    void Return() throws IOException {
        MainView.getMV().back();
    }
}
