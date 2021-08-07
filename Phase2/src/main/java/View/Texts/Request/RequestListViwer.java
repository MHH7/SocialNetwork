package View.Texts.Request;

import Controller.DataBase.DataBase;
import Model.Request;
import View.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RequestListViwer implements Initializable {
    private ArrayList<Integer> requests;

    @FXML
    private VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        requests = DataBase.getDB().getCurrentUser().getRequests();
        for(int i = 0;i < requests.size();i++){
            Request request = (Request) DataBase.getDB().getText(requests.get(i));
            MiniRequstViewer miniRequstViewer = new MiniRequstViewer(request);
            if(!request.isRead())vBox.getChildren().add(miniRequstViewer.getPane());
        }
    }

    @FXML
    void Return() throws IOException {
        MainView.getMV().back();
    }
}
