package View;

import Config.Config;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageViewer implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void explorer() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"explorer"));
    }

    @FXML
    void messenger() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"messenger"));
    }

    @FXML
    void personalPage() throws IOException {
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"personalPage"));
    }

    @FXML
    void settings() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"settings"));
    }

    @FXML
    void timeLine() throws IOException{
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"timeLine"));
    }
}
