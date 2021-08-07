package View;

import Config.Config;
import Listener.ExplorerListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExplorerViewer implements Initializable {
    private ExplorerListener explorerListener;

    @FXML
    private TextField textBox;

    @FXML
    private Pane pane;

    @FXML
    private Label label;

    public Label getLabel() {
        return label;
    }

    public Pane getPane() {
        return pane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        explorerListener = new ExplorerListener(this);
        label.setVisible(false);
        pane.setVisible(false);
    }

    @FXML
    void search() {
        explorerListener.search(textBox.getText());
    }

    @FXML
    void timeLine() throws IOException {
        MainView.getMV().setScene(Config.getConfig("FXMLLoader").getProperty(String.class,"timeLine"));
    }

    @FXML
    void Return() throws IOException {
        MainView.getMV().back();
    }
}
