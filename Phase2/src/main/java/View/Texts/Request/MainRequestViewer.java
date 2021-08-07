package View.Texts.Request;

import Controller.DataBase.DataBase;
import Listener.Texts.RequestListener;
import Model.Request;
import View.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainRequestViewer implements Initializable {
    private RequestListener requestListener;
    private Request request;

    @FXML
    private Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        request = DataBase.getDB().getCurrentRequest();
        requestListener = new RequestListener(request);
        label.setText(request.show());
    }

    @FXML
    void Return() throws IOException {
        MainView.getMV().back();
    }

    @FXML
    void accept() throws IOException{
        requestListener.accept();
        MainView.getMV().back();
    }

    @FXML
    void anonymousReject() throws IOException{
        requestListener.anonynousReject();
        MainView.getMV().back();
    }

    @FXML
    void reject() throws IOException{
        requestListener.reject();
        MainView.getMV().back();
    }
}
